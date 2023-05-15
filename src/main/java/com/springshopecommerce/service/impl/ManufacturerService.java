package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.dto.CloudinaryDTO;
import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ManufacturerEntity;
import com.springshopecommerce.repository.ManufacturerRepository;
import com.springshopecommerce.service.IManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManufacturerService implements IManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ManufacturerDTO> getManufacturers() {
        return manufacturerRepository.findAllManufacturers()
                .stream()
                .map(ManufacturerEntity-> modelMapper.map(ManufacturerEntity, ManufacturerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        CloudinaryDTO cloudinaryDTO = cloudinaryService.upload(manufacturerDTO.getLogoFile());
        ManufacturerEntity manufacturerEntity = new ManufacturerEntity();
        manufacturerEntity.setName(manufacturerDTO.getName());
        manufacturerEntity.setPublicId(cloudinaryDTO.getPublicId());
        manufacturerEntity.setLogoUrl(cloudinaryDTO.getUrl());

        return modelMapper.map((manufacturerRepository.save(manufacturerEntity)), ManufacturerDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO) {
        ManufacturerEntity manufacturerEntityOld = manufacturerRepository.getManufacturerEntitiesById(manufacturerDTO.getId());
        CloudinaryDTO cloudinaryDTO = new CloudinaryDTO();
        cloudinaryDTO = cloudinaryService.update(manufacturerEntityOld.getPublicId(), manufacturerDTO.getLogoFile());
        manufacturerDTO.setLogoUrl(cloudinaryDTO.getUrl());
        manufacturerDTO.setPublicId(cloudinaryDTO.getPublicId());
        this.modelMapper.map(manufacturerDTO, manufacturerEntityOld);
        return modelMapper.map(this.manufacturerRepository.save(manufacturerEntityOld), ManufacturerDTO.class);
    }

    @Override
    public ManufacturerDTO findByNameIgnoreCase(String name) {
        Optional<ManufacturerEntity> manufacturerEntityOptional = manufacturerRepository.findByNameIgnoreCase(name);
        if(manufacturerEntityOptional.isPresent()){
            ManufacturerEntity manufacturer = manufacturerEntityOptional.get();
            return modelMapper.map(manufacturer, ManufacturerDTO.class);
        }else {
            return new ManufacturerDTO();
        }
    }

    @Override
    public ManufacturerDTO getManufacturerEntitiesById(Long id) {
        ManufacturerEntity manufacturerEntity = manufacturerRepository.getManufacturerEntitiesById(id);
        return modelMapper.map(manufacturerEntity, ManufacturerDTO.class);
    }

    @Override
    public Page<ManufacturerEntity> searchManufacturersPaginged(String name, Pageable pageable) {
        return manufacturerRepository.getAllManufacturersPaginged(name, pageable);
    }

    @Override
    public Page<ManufacturerEntity> findAllManufacturersPaginged(Pageable pageable) {
        return manufacturerRepository.findAllManufacturersPaginged(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteManufacturerEntityById(Long id) {
        ManufacturerEntity manufacturerEntity = manufacturerRepository.getManufacturerEntitiesById(id);
        cloudinaryService.delete(manufacturerEntity.getPublicId());
        manufacturerRepository.deleteManufacturerEntityById(id);
    }
}
