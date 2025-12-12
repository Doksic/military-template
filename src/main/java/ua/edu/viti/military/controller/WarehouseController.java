package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.WarehouseCreateDTO;
import ua.edu.viti.military.dto.response.WarehouseResponseDTO;
import ua.edu.viti.military.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Tag(name = "3. Склади (Warehouses)", description = "Управління місцями зберігання")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    @Operation(summary = "Створити новий склад")
    public ResponseEntity<WarehouseResponseDTO> create(@Valid @RequestBody WarehouseCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Отримати список всіх складів")
    public ResponseEntity<List<WarehouseResponseDTO>> getAll() {
        return ResponseEntity.ok(warehouseService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про склад за ID")
    public ResponseEntity<WarehouseResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані про склад")
    public ResponseEntity<WarehouseResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseCreateDTO dto) {
        return ResponseEntity.ok(warehouseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити склад")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}