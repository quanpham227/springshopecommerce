package com.springshopecommerce.repository;

import com.springshopecommerce.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDTO> filterProducts(List<Long> manufacturers, List<String> cpus, List<String> rams,List<String> colors, List<String> screenSizes, String name, Pageable pageable);

}
