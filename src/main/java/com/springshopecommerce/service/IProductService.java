package com.springshopecommerce.service;


import com.springshopecommerce.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {


    Page<ProductDTO> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<ProductDTO> findAllProductsPaginged(Pageable pageable);
    ProductDTO findByProductId(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProductById(Long id);

}
