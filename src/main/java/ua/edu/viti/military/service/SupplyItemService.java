package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyItemCreateDTO;
import ua.edu.viti.military.dto.response.SupplyCategoryResponseDTO;
import ua.edu.viti.military.dto.response.SupplyItemResponseDTO;
import ua.edu.viti.military.entity.ItemStatus;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.entity.SupplyItem;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.exception.DuplicateResourceException;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.repository.SupplyCategoryRepository;
import ua.edu.viti.military.repository.SupplyItemRepository;
import ua.edu.viti.military.repository.WarehouseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SupplyItemService {

    private final SupplyItemRepository itemRepository;
    private final SupplyCategoryRepository categoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public SupplyItemResponseDTO create(SupplyItemCreateDTO dto) {
        log.info("Створення нового матеріалу: {}", dto.getBatchNumber());

        if (itemRepository.existsByBatchNumber(dto.getBatchNumber())) {
            throw new DuplicateResourceException("Партія " + dto.getBatchNumber() + " вже існує");
        }

        SupplyCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Категорію не знайдено"));

        Warehouse warehouse = null;
        if (dto.getWarehouseId() != null) {
            warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Склад не знайдено"));
        }

        SupplyItem item = new SupplyItem();
        item.setName(dto.getName());
        item.setBatchNumber(dto.getBatchNumber());
        item.setCategory(category);
        item.setQuantity(dto.getQuantity());
        item.setUnit(dto.getUnit());
        item.setExpirationDate(dto.getExpirationDate());
        item.setHazardClass(dto.getHazardClass());
        item.setStorageConditions(dto.getStorageConditions());
        item.setWarehouse(warehouse);
        item.setStatus(ItemStatus.IN_STOCK);

        return toDTO(itemRepository.save(item));
    }

    public List<SupplyItemResponseDTO> getAll(Long categoryId, ItemStatus status) {
        List<SupplyItem> items;

        if (categoryId != null && status != null) {
            items = itemRepository.findByStatusAndCategoryId(status, categoryId);
        } else if (categoryId != null) {
            items = itemRepository.findByCategoryId(categoryId);
        } else if (status != null) {
            items = itemRepository.findByStatus(status);
        } else {
            items = itemRepository.findAll();
        }

        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<SupplyItemResponseDTO> getExpiringSoon(int days) {
        LocalDate thresholdDate = LocalDate.now().plusDays(days);

        return itemRepository.findByExpirationDateBefore(thresholdDate).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SupplyItemResponseDTO getById(Long id) {
        return itemRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Матеріал не знайдено"));
    }

    @Transactional
    public SupplyItemResponseDTO update(Long id, SupplyItemCreateDTO dto) {
        SupplyItem item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Матеріал не знайдено"));

        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        item.setUnit(dto.getUnit());
        item.setExpirationDate(dto.getExpirationDate());
        item.setHazardClass(dto.getHazardClass());
        item.setStorageConditions(dto.getStorageConditions());

        if (!item.getCategory().getId().equals(dto.getCategoryId())) {
            SupplyCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Категорію не знайдено"));
            item.setCategory(category);
        }

        if (dto.getWarehouseId() != null) {
            if (item.getWarehouse() == null || !item.getWarehouse().getId().equals(dto.getWarehouseId())) {
                Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Склад не знайдено"));
                item.setWarehouse(warehouse);
            }
        } else {
            item.setWarehouse(null);
        }

        return toDTO(itemRepository.save(item));
    }

    @Transactional
    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Матеріал не знайдено");
        }
        itemRepository.deleteById(id);
    }

    private SupplyItemResponseDTO toDTO(SupplyItem item) {
        SupplyItemResponseDTO dto = new SupplyItemResponseDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setBatchNumber(item.getBatchNumber());
        dto.setQuantity(item.getQuantity());
        dto.setUnit(item.getUnit());
        dto.setExpirationDate(item.getExpirationDate());
        dto.setHazardClass(item.getHazardClass());
        dto.setStorageConditions(item.getStorageConditions());
        dto.setStatus(item.getStatus());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setUpdatedAt(item.getUpdatedAt());

        SupplyCategoryResponseDTO catDto = new SupplyCategoryResponseDTO();
        catDto.setId(item.getCategory().getId());
        catDto.setName(item.getCategory().getName());
        catDto.setCode(item.getCategory().getCode());
        dto.setCategory(catDto);

        if (item.getWarehouse() != null) {
            dto.setWarehouseId(item.getWarehouse().getId());
            dto.setWarehouseName(item.getWarehouse().getName());
        }

        return dto;
    }
}