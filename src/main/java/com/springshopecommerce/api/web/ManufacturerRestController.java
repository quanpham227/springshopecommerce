package com.springshopecommerce.api.web;

import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/api/manufacturers")
public class ManufacturerRestController {

    @Autowired
    private IManufacturerService manufacturerService;



    @GetMapping()
    public List<ManufacturerDTO> getManufacturers(){
        return manufacturerService.getIdAndNameAndLogoManufacturer();
    }

}
