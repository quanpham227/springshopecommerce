package com.springshopecommerce.service;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getIdAndNameCategory();
    Page<CategoryDTO> searchCategoryPaginged(String name, Pageable pageable);
    Page<CategoryDTO> findAllPaginged(Pageable pageable);
    CategoryDTO findByCategoryId(Long id);
    CategoryDTO getIdAndNameCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);

}
