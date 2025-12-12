package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.SupplyItemCreateDTO;
import ua.edu.viti.military.dto.response.SupplyItemResponseDTO;
import ua.edu.viti.military.entity.ItemStatus;
import ua.edu.viti.military.service.SupplyItemService;

import java.util.List;

@RestController
@RequestMapping("/api/supply-items")
@RequiredArgsConstructor
@Tag(name = "2. Матеріали (Items)", description = "Облік матеріальних засобів")
public class SupplyItemController {

    private final SupplyItemService itemService;

    @PostMapping
    @Operation(summary = "Створити новий матеріал", description = "Створює картку обліку матеріалу. Потрібен існуючий ID категорії.")
    public ResponseEntity<SupplyItemResponseDTO> create(@Valid @RequestBody SupplyItemCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Отримати всі матеріали", description = "Підтримує фільтрацію за категорією та статусом")
    public ResponseEntity<List<SupplyItemResponseDTO>> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) ItemStatus status) {
        return ResponseEntity.ok(itemService.getAll(categoryId, status));
    }

    @GetMapping("/expiring-soon")
    @Operation(summary = "Матеріали з терміном що закінчується",
            description = "Повертає список матеріалів, термін придатності яких спливає у вказану кількість днів")
    public ResponseEntity<List<SupplyItemResponseDTO>> getExpiringSoon(
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(itemService.getExpiringSoon(days));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати матеріал за ID")
    public ResponseEntity<SupplyItemResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані про матеріал")
    public ResponseEntity<SupplyItemResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplyItemCreateDTO dto) {
        return ResponseEntity.ok(itemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити матеріал")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}