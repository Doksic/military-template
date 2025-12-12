package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ua.edu.viti.military.entity.HazardClass;

import java.time.LocalDate;

@Data
public class SupplyItemCreateDTO {
    @NotBlank(message = "Назва обов'язкова")
    private String name;

    @NotBlank(message = "Номер партії обов'язковий")
    private String batchNumber;

    @NotNull(message = "Категорія обов'язкова")
    private Long categoryId;

    @NotNull
    @Positive
    private Integer quantity;

    private String unit = "шт";

    @Future(message = "Термін придатності має бути в майбутньому")
    private LocalDate expirationDate;

    @NotNull
    private HazardClass hazardClass;

    private String storageConditions;

    private Long warehouseId;
}