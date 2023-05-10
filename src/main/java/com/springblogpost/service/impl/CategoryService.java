package com.springblogpost.service.impl;

import com.springblogpost.dto.CategoryDTO;
import com.springblogpost.entity.CategoryEntity;
import com.springblogpost.repository.CategoryRepository;
import com.springblogpost.service.ICategoryService;
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
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.getAllCategories().stream()
                .map(CategoryEntity -> modelMapper.map(CategoryEntity,CategoryDTO.class))
                .collect(Collectors.toList());
        return categories;
    }

    @Override
    public Page<CategoryEntity> searchCategoryPaginged(String name, Pageable pageable) {
        return categoryRepository.searchCategoryPaginged(name, pageable);
    }

    @Override
    public Page<CategoryEntity> findAllPaginged(Pageable pageable) {
        return categoryRepository.findAllPaginged( pageable);
    }

    @Override
    public CategoryDTO findByCategoryId(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(id);
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findByCategoryName(String name) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByCategoryName(name);

       if(categoryEntityOptional.isPresent()){
           CategoryEntity categoryEntity = categoryEntityOptional.get();
           return modelMapper.map(categoryEntity, CategoryDTO.class);
       }else {
           return new CategoryDTO();
       }
    }

    @Override
    public List<CategoryDTO> findByNameContaining(String name) {
        List<CategoryDTO>  categoryDTOList = categoryRepository.findByNameContaining(name)
                .stream().map(CategoryEntity -> modelMapper.map(CategoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        return modelMapper.map(categoryRepository.save(categoryEntity), CategoryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntityOld = this.categoryRepository.findByCategoryId(categoryDTO.getId());
        this.modelMapper.map(categoryDTO, categoryEntityOld);
        return modelMapper.map(this.categoryRepository.save(categoryEntityOld), CategoryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteCategoryById(id);
    }
}
