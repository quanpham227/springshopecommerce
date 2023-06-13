package com.springshopecommerce.api.web;

import com.springshopecommerce.dto.FilterRequest;
import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.service.IManufacturerService;
import com.springshopecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/api/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;


    @PostMapping("/filter")
    public List<ProductDTO> filterProducts(@RequestParam(defaultValue = "0") int start,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                           @RequestParam(value = "sort",defaultValue = "asc", required = false) String sortDirection,
                                           @RequestBody FilterRequest filterRequest) {
        List<Long> manufacturers = filterRequest.getManufacturers();
        List<String> cpus = filterRequest.getCpus();
        List<String> rams = filterRequest.getRams();
        List<String> colors = filterRequest.getColors();
        List<String> screenSizes = filterRequest.getScreenSizes();
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Sort sortObject = Sort.by(direction, "price");
        Pageable pageable = PageRequest.of(start , limit, sortObject);

        List<ProductDTO> products = null;
        if(filterRequest.isEmptyLists()) {
            products = productService.getProductsByNameAndPage(name, pageable);
        }else {
            products = productService.filterProducts(manufacturers,cpus,rams,colors, screenSizes, name, pageable);
        }

       return products;
    }


}
