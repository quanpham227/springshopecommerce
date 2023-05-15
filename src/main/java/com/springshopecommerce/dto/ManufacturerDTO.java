package com.springshopecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDTO extends AbstractDTO<ManufacturerDTO> implements Serializable {
    @NotEmpty
    private String name;
    private String logoUrl;
    private String publicId;

    @JsonIgnore
    private MultipartFile logoFile;

    private Boolean isEdit = false;
}
