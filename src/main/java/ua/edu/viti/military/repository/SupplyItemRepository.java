package ua.edu.viti.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.viti.military.entity.HazardClass;
import ua.edu.viti.military.entity.ItemStatus;
import ua.edu.viti.military.entity.SupplyItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {

    Optional<SupplyItem> findByBatchNumber(String batchNumber);

    boolean existsByBatchNumber(String batchNumber);

    List<SupplyItem> findByCategoryId(Long categoryId);

    @Query("SELECT i FROM SupplyItem i JOIN FETCH i.category WHERE i.status = :status")
    List<SupplyItem> findByStatusWithCategory(@Param("status") ItemStatus status);

    List<SupplyItem> findByStatus(ItemStatus status);

    List<SupplyItem> findByWarehouseId(Long warehouseId);

    List<SupplyItem> findByHazardClass(HazardClass hazardClass);

    List<SupplyItem> findByStatusAndCategoryId(ItemStatus status, Long categoryId);

    List<SupplyItem> findByExpirationDateBefore(LocalDate date);
}