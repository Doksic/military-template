package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.WarehouseCreateDTO;
import ua.edu.viti.military.dto.response.WarehouseResponseDTO;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.exception.DuplicateResourceException;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.repository.WarehouseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Transactional
    public WarehouseResponseDTO create(WarehouseCreateDTO dto) {
        if (warehouseRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("Склад з кодом " + dto.getCode() + " вже існує");
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setCode(dto.getCode());
        warehouse.setAddress(dto.getAddress());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setCurrentOccupancy(dto.getCurrentOccupancy());
        warehouse.setHasRefrigeration(dto.getHasRefrigeration());

        return toDTO(warehouseRepository.save(warehouse));
    }

    public List<WarehouseResponseDTO> getAll() {
        return warehouseRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public WarehouseResponseDTO getById(Long id) {
        return warehouseRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Склад не знайдено"));
    }

    @Transactional
    public WarehouseResponseDTO update(Long id, WarehouseCreateDTO dto) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Склад не знайдено"));

        warehouse.setName(dto.getName());
        warehouse.setCode(dto.getCode());
        warehouse.setAddress(dto.getAddress());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setCurrentOccupancy(dto.getCurrentOccupancy());
        warehouse.setHasRefrigeration(dto.getHasRefrigeration());

        return toDTO(warehouseRepository.save(warehouse));
    }

    @Transactional
    public void delete(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Склад не знайдено");
        }
        warehouseRepository.deleteById(id);
    }

    private WarehouseResponseDTO toDTO(Warehouse entity) {
        WarehouseResponseDTO dto = new WarehouseResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setAddress(entity.getAddress());
        dto.setCapacity(entity.getCapacity());
        dto.setCurrentOccupancy(entity.getCurrentOccupancy());
        dto.setHasRefrigeration(entity.getHasRefrigeration());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}