package com.springshopecommerce.repository;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.dto.ManufacturerDTO;
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

    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl ) " +
            "FROM ManufacturerEntity m ")
    List<ManufacturerDTO> getIdAndNameAndLogoManufacturer();

    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl) " +
            "FROM ManufacturerEntity m ")
    Page<ManufacturerDTO> findAllManufacturers(Pageable pageable);
    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl, COUNT(p)) " +
            "FROM ManufacturerEntity m " +
            "LEFT JOIN m.products p " +
            "GROUP BY m.id")
    List<ManufacturerDTO> getIdNameLogoAndProductCount();
    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl) " +
            "FROM ManufacturerEntity m " +
            "WHERE LOWER(m.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<ManufacturerDTO> searchManufacturersByName (String name, Pageable pageable);

    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl, m.fileName) " +
            "FROM ManufacturerEntity m " +
            "WHERE m.id = :manufacturerId")
    Optional<ManufacturerDTO> findIdAndNameAndLogoUrlByManufacturerId(@Param("manufacturerId") Long id);

    @Query("SELECT new com.springshopecommerce.dto.ManufacturerDTO(m.id, m.name, m.logoUrl) " +
            "FROM ManufacturerEntity m " +
            "WHERE LOWER(m.name) LIKE LOWER(concat('%', :name, '%'))")
    ManufacturerDTO findManufacturerEntitiesByNameIgnoreCase(String name);


    @Query("SELECT new com.springshopecommerce.entity.ManufacturerEntity(m.id, m.name, m.logoUrl, m.publicId, m.fileName) " +
            "FROM ManufacturerEntity m " +
            "WHERE m.id = :manufacturerId")
    Optional<ManufacturerEntity> findManufacturerEntityById(@Param("manufacturerId") Long id);



    @Modifying
    @Query("DELETE FROM ManufacturerEntity m WHERE m.id = :id")
    void deleteManufacturerEntityById(@Param("id") Long id);
}
