package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.ProductImageDTO;
import com.springshopecommerce.repository.ProductImageRepository;
import com.springshopecommerce.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Override
    public List<ProductImageDTO> getProductImagesByProductId(Long id) {
       List<ProductImageDTO> productImageDTOList = productImageRepository.getProductImagesByProductId(id);
       return productImageDTOList;
    }
}
