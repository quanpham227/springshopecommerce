package com.springshopecommerce.service;

import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ManufacturerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManufacturerService {
    List<ManufacturerDTO> getManufacturers();
    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);
    ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO);

    ManufacturerDTO findByNameIgnoreCase ( String name);

    ManufacturerDTO getManufacturerEntitiesById (Long id);



    Page<ManufacturerEntity> searchManufacturersPaginged(String name, Pageable pageable);
    Page<ManufacturerEntity> findAllManufacturersPaginged(Pageable pageable);

    void deleteManufacturerEntityById (Long id);
}
