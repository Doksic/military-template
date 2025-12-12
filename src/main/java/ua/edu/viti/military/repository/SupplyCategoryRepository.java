package ua.edu.viti.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.viti.military.entity.SupplyCategory;

import java.util.Optional;

@Repository
public interface SupplyCategoryRepository extends JpaRepository<SupplyCategory, Long> {
    Optional<SupplyCategory> findByName(String name);

    boolean existsByName(String name);

    Optional<SupplyCategory> findByCode(String code);

    boolean existsByCode(String code);
}