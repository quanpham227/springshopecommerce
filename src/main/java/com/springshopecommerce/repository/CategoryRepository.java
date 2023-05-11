package com.springshopecommerce.repository;

import com.springshopecommerce.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query(value = "select c from CategoryEntity c")
    List<CategoryEntity> getAllCategories();

    @Query(value = "select c from CategoryEntity c ")
    Page<CategoryEntity> findAllPaginged(Pageable pageable);


    @Query(value = "select c from CategoryEntity c where c.name LIKE ?1% ")
    Page<CategoryEntity> searchCategoryPaginged (String name, Pageable pageable);

    @Query(value = "select c from CategoryEntity c where c.name = ?1")
    Optional<CategoryEntity> findByCategoryName(String name);

    @Query(value = "select c from CategoryEntity c where c.name = ?1")
    List<CategoryEntity> findByNameContaining(String name);

    @Query(value = "select c from CategoryEntity c where c.id = ?1")
    CategoryEntity findByCategoryId(Long id);

    @Modifying
    @Query(value = "delete from CategoryEntity c where c.id = ?1")
    void deleteCategoryById(Long id);
}