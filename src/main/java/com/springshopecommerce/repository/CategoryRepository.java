package com.springshopecommerce.repository;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.entity.CategoryEntity;
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
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query("SELECT new com.springshopecommerce.dto.CategoryDTO(c.id, c.name) " +
            "FROM CategoryEntity c ")
    List<CategoryDTO> getIdAndNameCategory();

    @Query("SELECT new com.springshopecommerce.dto.CategoryDTO(c.id, c.name) " +
            "FROM CategoryEntity c ")
    Page<CategoryDTO> findAllPaginged(Pageable pageable);


    @Query("SELECT new com.springshopecommerce.dto.CategoryDTO(c.id, c.name) " +
            "FROM CategoryEntity c " +
            "WHERE LOWER(c.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<CategoryDTO> searchCategoryPaginged (String name, Pageable pageable);



    @Query("SELECT new com.springshopecommerce.dto.CategoryDTO(c.id, c.name) " +
            "FROM CategoryEntity c " +
            "WHERE c.id = :categoryId")
    Optional<CategoryDTO> findByCategoryId(@Param("categoryId") Long id);
    @Query("SELECT new com.springshopecommerce.entity.CategoryEntity(c.id, c.name) " +
            "FROM CategoryEntity c " +
            "WHERE c.id = :categoryId")
    Optional<CategoryEntity> findById(@Param("categoryId") Long id);
    @Query(value = "select c from CategoryEntity c where c.id= ?1")
    Optional<CategoryEntity> findByCategoryIdForUpdate(Long id);


    @Query("SELECT new com.springshopecommerce.dto.CategoryDTO(c.id, c.name) " +
            "FROM CategoryEntity c " +
            "WHERE c.id = :categoryId")
    CategoryDTO getIdAndNameCategoryById (@Param("categoryId") Long id);


    @Modifying
    @Query(value = "delete from CategoryEntity c where c.id = ?1")
    void deleteCategoryById(Long id);
}