package com.springshopecommerce.service;

import com.springshopecommerce.dto.ProductImageDTO;

import java.util.List;

public interface IProductImageService {

    List<ProductImageDTO> getProductImagesByProductId (Long id);
}
