package ua.edu.viti.military.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplyCategoryResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean requiresColdStorage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}