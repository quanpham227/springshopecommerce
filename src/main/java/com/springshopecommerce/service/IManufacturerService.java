package com.springshopecommerce.service;

import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ManufacturerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManufacturerService {
    List<ManufacturerDTO> getIdAndNameAndLogoManufacturer();
    List<ManufacturerDTO> getIdNameLogoAndProductCount();


    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);
    ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO);

    ManufacturerDTO findManufacturerByNameIgnoreCase ( String name);

    ManufacturerDTO getManufacturerEntitiesById (Long id);



    Page<ManufacturerDTO> searchManufacturersByName(String name, Pageable pageable);
    Page<ManufacturerDTO> findAllManufacturers(Pageable pageable);

    void deleteManufacturerEntityById (Long id);
}
