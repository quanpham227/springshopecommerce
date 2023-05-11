package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.ProductEntity;
import com.springshopecommerce.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public Page<ProductEntity> searchProductPaginged(String name, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductEntity> findAllPaginged(Pageable pageable) {
        return null;
    }

    @Override
    public ProductDTO findByProductId(Long id) {
        return null;
    }

    @Override
    public ProductDTO findByProductName(String name) {
        return null;
    }

    @Override
    public List<ProductDTO> findByNameContaining(String name) {
        return null;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }
}
