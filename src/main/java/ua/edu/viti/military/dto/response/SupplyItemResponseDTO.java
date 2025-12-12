package ua.edu.viti.military.dto.response;

import lombok.Data;
import ua.edu.viti.military.entity.HazardClass;
import ua.edu.viti.military.entity.ItemStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SupplyItemResponseDTO {
    private Long id;
    private String name;
    private String batchNumber;

    private SupplyCategoryResponseDTO category;

    private Integer quantity;
    private String unit;
    private LocalDate expirationDate;
    private HazardClass hazardClass;
    private String storageConditions;

    private Long warehouseId;
    private String warehouseName;

    private ItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}