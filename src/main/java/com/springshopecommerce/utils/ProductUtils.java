package com.springshopecommerce.utils;

import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.ProductEntity;

import java.util.Date;

public class ProductUtils {

    public ProductEntity mapDtoToEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();

        if(productDTO.getId() != null){
            productEntity.setId(productDTO.getId());
        }
        if (productDTO.getName() != null) {
            productEntity.setName(productDTO.getName());
        }
        productEntity.setQuantity(productDTO.getQuantity());

        if (productDTO.getPrice() != null) {
            productEntity.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDiscount() != null) {
            productEntity.setDiscount(productDTO.getDiscount());
        }
        if (productDTO.getDescription() != null) {
            productEntity.setDescription(productDTO.getDescription());
        }
        if (productDTO.getStatus() != null) {
            productEntity.setStatus(productDTO.getStatus());
        }
        if (productDTO.getCreateDate() != null) {
            productEntity.setCreateDate(productDTO.getCreateDate());
        }
        if (productDTO.getUpdateDate() != null) {
            productEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        }
        return productEntity;
    }


}
