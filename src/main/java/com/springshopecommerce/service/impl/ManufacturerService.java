package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.CloudinaryDTO;
import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.entity.ManufacturerEntity;
import com.springshopecommerce.exception.NotFoundException;
import com.springshopecommerce.repository.ManufacturerRepository;
import com.springshopecommerce.service.IManufacturerService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class ManufacturerService implements IManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<ManufacturerDTO> getIdAndNameAndLogoManufacturer() {
        List<ManufacturerDTO> manufacturers = manufacturerRepository.getIdAndNameAndLogoManufacturer();
        return manufacturers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        String folderName = "manufacturers";
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(manufacturerDTO.getLogoFile().getOriginalFilename());

        CloudinaryDTO cloudinaryDTO = cloudinaryService.upload(manufacturerDTO.getLogoFile(), folderName);

        ManufacturerEntity manufacturerEntity = new ManufacturerEntity();
        manufacturerEntity.setName(manufacturerDTO.getName());
        manufacturerEntity.setPublicId(cloudinaryDTO.getPublicId());
        manufacturerEntity.setLogoUrl(cloudinaryDTO.getUrl());
        manufacturerEntity.setFileName(fileName);
        ManufacturerEntity manufacturerSave = manufacturerRepository.save(manufacturerEntity);

        ManufacturerDTO manufacturerResponse = new ManufacturerDTO();
        manufacturerResponse.setId(manufacturerSave.getId());
        manufacturerResponse.setName(manufacturerSave.getName());
        manufacturerResponse.setLogoUrl(manufacturerSave.getLogoUrl());
        manufacturerResponse.setFileName(manufacturerResponse.getFileName());
        return  manufacturerResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ManufacturerDTO updateManufacturer(ManufacturerDTO manufacturerDTO) {
        ManufacturerEntity manufacturerEntityOld = manufacturerRepository.findManufacturerEntityById(manufacturerDTO.getId())
                .orElseThrow(() -> new NotFoundException("cannot find manufacturer with :" + manufacturerDTO.getId()));

        if(manufacturerDTO.getLogoFile() != null){
            String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(manufacturerDTO.getLogoFile().getOriginalFilename());
            CloudinaryDTO cloudinaryDTO = new CloudinaryDTO();
            cloudinaryDTO = cloudinaryService.update(manufacturerEntityOld.getPublicId(), manufacturerDTO.getLogoFile());
            manufacturerEntityOld.setLogoUrl(cloudinaryDTO.getUrl());
            manufacturerEntityOld.setPublicId(cloudinaryDTO.getPublicId());
            manufacturerEntityOld.setFileName(fileName);

        }
        manufacturerEntityOld.setName(manufacturerDTO.getName());
        ManufacturerEntity manufacturerEntity = manufacturerRepository.save(manufacturerEntityOld);

        ManufacturerDTO manufacturer = new ManufacturerDTO();
        manufacturer.setId(manufacturerEntity.getId());
        manufacturer.setName(manufacturerEntity.getName());
        manufacturer.setLogoUrl(manufacturerEntity.getLogoUrl());

        return manufacturer;
    }

    @Override
    public ManufacturerDTO findManufacturerByNameIgnoreCase(String name) {
        ManufacturerDTO manufacturerDTO = manufacturerRepository.findManufacturerEntitiesByNameIgnoreCase(name);
        return manufacturerDTO;
    }

    @Override
    public ManufacturerDTO getManufacturerEntitiesById(Long id) {
        ManufacturerDTO manufacturerDTO = manufacturerRepository.findIdAndNameAndLogoUrlByManufacturerId(id)
                .orElseThrow(() -> new NotFoundException("cannot find manufacturer with :" + id));
        return manufacturerDTO;
    }

    @Override
    public Page<ManufacturerDTO> searchManufacturersByName(String name, Pageable pageable) {
        Page<ManufacturerDTO> manufacturerDTOPage = manufacturerRepository.searchManufacturersByName(name, pageable);
        return manufacturerDTOPage;
    }

    @Override
    public Page<ManufacturerDTO> findAllManufacturers(Pageable pageable) {
        Page<ManufacturerDTO> manufacturerDTOPage = manufacturerRepository.findAllManufacturers( pageable);
        return manufacturerDTOPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteManufacturerEntityById(Long id) {
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findManufacturerEntityById(id)
                .orElseThrow(() -> new NotFoundException("cannot find manufacturer with :" + id));
        cloudinaryService.delete(manufacturerEntity.getPublicId());
        manufacturerRepository.deleteManufacturerEntityById(id);
    }
}
