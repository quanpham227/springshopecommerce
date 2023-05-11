package com.springshopecommerce.dto;
import com.springshopecommerce.entity.ProductStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProductDTO extends AbstractDTO<ProductDTO> implements Serializable {
    @NotEmpty(message = "Name is required")
    private String name;

    @Min(value = 0)
    private Integer quantity;

    @Min(value = 0)
    private Double unitPrice;

    private String image;

    @Min(value = 0)
    @Max(value = 100)
    private Float discount;

    private String description;


    private Date entereDate;

    private ProductStatus status;

    private Long categoryId;

    private Long manufacturerId;

    private Boolean isEdit = false;

    private MultipartFile imageFile;

}
