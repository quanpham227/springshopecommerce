package com.springshopecommerce.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageDTO extends AbstractDTO<ProductImageDTO> implements Serializable {


    private String name;

    private String url;

}
