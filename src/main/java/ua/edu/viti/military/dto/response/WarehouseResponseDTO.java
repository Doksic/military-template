package ua.edu.viti.military.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WarehouseResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String address;
    private Integer capacity;
    private Integer currentOccupancy;
    private Boolean hasRefrigeration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}