package com.springshopecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springshopecommerce.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDTO extends AbstractDTO<ManufacturerDTO> implements Serializable {
    @NotEmpty
    private String name;

    private String logoUrl;

    private String publicId;

    private String fileName;

    @JsonIgnore
    private MultipartFile logoFile;

    private Boolean isEdit = false;

    private List<ProductDTO> products = new ArrayList<>();
    public ManufacturerDTO(String name) {
        this.name = name;
    }

    public ManufacturerDTO(Long id, String name, String logoUrl, String publicId, MultipartFile logoFile, Boolean isEdit) {
        super(id);
        this.name = name;
        this.logoUrl = logoUrl;
        this.publicId = publicId;
        this.logoFile = logoFile;
        this.isEdit = isEdit;
    }
    public ManufacturerDTO(Long id, String name, String logoUrl) {
        super(id);
        this.name = name;
        this.logoUrl = logoUrl;
    }
    public ManufacturerDTO(Long id, String name, String logoUrl, String fileName) {
        super(id);
        this.name = name;
        this.logoUrl = logoUrl;
        this.fileName = fileName;
    }
}
