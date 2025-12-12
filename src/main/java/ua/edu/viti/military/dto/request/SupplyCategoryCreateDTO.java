package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplyCategoryCreateDTO {
    @NotBlank(message = "Назва обов'язкова")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Код обов'язковий")
    @Size(max = 20)
    private String code;

    @Size(max = 500)
    private String description;

    private Boolean requiresColdStorage = false;
}