package com.springshopecommerce.dto;

import com.springshopecommerce.entity.ProductEntity;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductImageDTO extends AbstractDTO<ProductImageDTO> implements Serializable {

    private String publicId;

    private String fileName;

    private String url;


    public ProductImageDTO(Long id, String publicId, String fileName, String url) {
        super(id);
        this.publicId = publicId;
        this.fileName = fileName;
        this.url = url;
    }

    public ProductImageDTO(Long id, String imageUrl) {
        super(id);
        this.url = imageUrl;
    }
}
