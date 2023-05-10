package com.springblogpost.service;

import com.springblogpost.dto.CategoryDTO;
import com.springblogpost.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    Page<CategoryEntity> searchCategoryPaginged(String name, Pageable pageable);
    Page<CategoryEntity> findAllPaginged(Pageable pageable);

    CategoryDTO findByCategoryId(Long id);
    CategoryDTO findByCategoryName(String name);
    List<CategoryDTO> findByNameContaining(String name);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);

}
