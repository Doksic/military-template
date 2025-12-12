package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WarehouseCreateDTO {

    @NotBlank(message = "Назва складу обов'язкова")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Код складу обов'язковий")
    @Size(max = 20)
    private String code;

    @Size(max = 200)
    private String address;

    @Positive(message = "Місткість має бути позитивною")
    private Integer capacity;

    private Integer currentOccupancy = 0;

    private Boolean hasRefrigeration = false;
}