package com.springshopecommerce.repository;

import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ManufacturerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {

    @Query(value = "select m from ManufacturerEntity m")
    List<ManufacturerEntity> findAllManufacturers();
    @Query("SELECT e FROM ManufacturerEntity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ManufacturerEntity> findByNameContainsIgnoreCase(@Param("name") String name);

    @Query("SELECT m FROM ManufacturerEntity m WHERE LOWER(m.name) = LOWER(:name)")
    Optional<ManufacturerEntity> findByNameIgnoreCase(@Param("name") String name);
    @Query("SELECT m FROM ManufacturerEntity m WHERE m.id <> :id AND LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ManufacturerEntity> findByIdNotAndNameContainsIgnoreCase(Long id, String name);

    @Query("SELECT m FROM ManufacturerEntity m WHERE LOWER(m.name) LIKE %:name%")
    Page<ManufacturerEntity> getAllManufacturersPaginged(String name,Pageable pageable);

    @Query(value = "select m from ManufacturerEntity m ")
    Page<ManufacturerEntity> findAllManufacturersPaginged(Pageable pageable);

    @Query("select m from ManufacturerEntity m where m.id = ?1")
    ManufacturerEntity getManufacturerEntitiesById(Long id);

    @Modifying
    @Query("DELETE FROM ManufacturerEntity m WHERE m.id = :id")
    void deleteManufacturerEntityById(@Param("id") Long id);
}
