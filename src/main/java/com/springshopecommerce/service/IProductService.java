package com.springshopecommerce.service;


import com.springshopecommerce.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    List<ProductDTO> getAllProducts ();
    List<ProductDTO> getProductsByNameAndPage (String name, Pageable pageable);
    List<ProductDTO> filterProducts (List<Long> manufacturers, List<String> cpus, List<String> rams,List<String> colors, List<String> screenSizes,String name, Pageable pageable);
    List<ProductDTO> getFilteredProducts (Long manufacturerId, String cpu, String ram, String color, String screenSize);
    Page<ProductDTO> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<ProductDTO> findAllProductsPaginged(Pageable pageable);
    ProductDTO findByProductId(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProductById(Long id);

}
