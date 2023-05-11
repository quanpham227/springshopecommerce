package com.springshopecommerce.service;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    List<ProductDTO> getAllProducts();
    Page<ProductEntity> searchProductPaginged(String name, Pageable pageable);
    Page<ProductEntity> findAllPaginged(Pageable pageable);

    ProductDTO findByProductId(Long id);
    ProductDTO findByProductName(String name);
    List<ProductDTO> findByNameContaining(String name);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProductById(Long id);

}
