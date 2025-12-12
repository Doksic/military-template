package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.SupplyCategoryCreateDTO;
import ua.edu.viti.military.dto.response.SupplyCategoryResponseDTO;
import ua.edu.viti.military.service.SupplyCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/supply-categories")
@RequiredArgsConstructor
@Tag(name = "1. Категорії", description = "Управління категоріями МТЗ")
public class SupplyCategoryController {

    private final SupplyCategoryService categoryService;

    @PostMapping
    @Operation(summary = "Створити категорію")
    public ResponseEntity<SupplyCategoryResponseDTO> create(@Valid @RequestBody SupplyCategoryCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Отримати всі категорії")
    public ResponseEntity<List<SupplyCategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати категорію за ID")
    public ResponseEntity<SupplyCategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    // 👇 ДОДАНО: Update
    @PutMapping("/{id}")
    @Operation(summary = "Оновити категорію")
    public ResponseEntity<SupplyCategoryResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplyCategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    // 👇 ДОДАНО: Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити категорію")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}