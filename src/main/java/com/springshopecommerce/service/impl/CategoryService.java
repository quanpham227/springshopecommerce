package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.exception.NotFoundException;
import com.springshopecommerce.repository.CategoryRepository;
import com.springshopecommerce.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getIdAndNameCategory() {
        List<CategoryDTO> categories = categoryRepository.getIdAndNameCategory();
        return categories;
    }

    @Override
    public Page<CategoryDTO> searchCategoryPaginged(String name, Pageable pageable) {
        Page<CategoryDTO> CategoryDTOPage = categoryRepository.searchCategoryPaginged(name, pageable);
        return CategoryDTOPage;
    }

    @Override
    public Page<CategoryDTO> findAllPaginged(Pageable pageable) {
        Page<CategoryDTO> CategoryDTOPage = categoryRepository.findAllPaginged( pageable);
        return CategoryDTOPage;
    }

    @Override
    public CategoryDTO findByCategoryId(Long id) {
        CategoryDTO categoryDTO = this.categoryRepository.findByCategoryId(id)
                .orElseThrow(()->new NotFoundException("Category not found"));
        return categoryDTO;
    }

    @Override
    public CategoryDTO getIdAndNameCategoryById(Long id) {
        CategoryDTO categoryDTO = categoryRepository.getIdAndNameCategoryById(id);
        return categoryDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setName(categoryDTO.getName());
        CategoryEntity categorySave = categoryRepository.save(categoryEntity);
        CategoryDTO  category = new CategoryDTO();
        category.setId(categorySave.getId());
        category.setName(categorySave.getName());
        return categoryDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryOptional = categoryRepository.findByCategoryIdForUpdate(categoryDTO.getId())
                .orElseThrow(()->new NotFoundException("Category not found "));

        categoryOptional.setName(categoryDTO.getName());
        CategoryEntity category = categoryRepository.save(categoryOptional);
        CategoryDTO categoryResponse = new CategoryDTO();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoryById(Long id) {
        CategoryDTO categoryDTO = this.categoryRepository.findByCategoryId(id)
                .orElseThrow(() -> new NotFoundException("cannot find category with id :" + id ));
        categoryRepository.deleteCategoryById(id);
    }
}
