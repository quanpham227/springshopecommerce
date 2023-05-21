package com.springshopecommerce.repository;

import com.springshopecommerce.dto.ProductImageDTO;
import com.springshopecommerce.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    @Query("SELECT new com.springshopecommerce.dto.ProductImageDTO(i.id,i.publicId, i.fileName, i.url) " +
            "FROM ProductImageEntity i JOIN i.products p WHERE p.id = :productId")
    List<ProductImageDTO> getProductImagesByProductId(Long productId);

}
