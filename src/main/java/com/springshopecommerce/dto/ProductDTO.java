package com.springshopecommerce.dto;
import com.springshopecommerce.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends AbstractDTO<ProductDTO> implements Serializable {
    @NotEmpty
    private String name;

    private String cpu;

    private String ram;

    private String color;

    private String screenSize;

    @Min(value = 0)
    private int quantity;

    @Digits(integer = 11, fraction = 2)
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private BigDecimal price;

    @Min(value = 0)
    @Max(value = 100)
    private Float discount;

    private String description;

    private Long categoryId;

    private Long manufacturerId;

    private ProductStatus status;

    private Date createDate;

    private Date updateDate;

    private String categoryName;

    private String manufacturerName;

    private ManufacturerDTO manufacturer;

    private List<ProductImageDTO> images = new ArrayList<>();

    private ProductImageDTO image;

    private MultipartFile[] imageFile;

    private Boolean isEdit = false;

    public ProductDTO(Long id, String name, int quantity, BigDecimal price, String description, Float discount, ProductStatus status, Date createDate, Date updateDate, ProductImageEntity image, String categoryName, String manufacturerName, Long categoryId, Long manufacturerId) {
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.image = new ProductImageDTO(image.getId(), image.getPublicId(), image.getFileName(), image.getUrl());
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }
    public ProductDTO(Long id, String name,String cpu, String ram, String color, String screenSize, int quantity, BigDecimal price, String description, Float discount, ProductStatus status, Date createDate, Date updateDate, ProductImageEntity image, String categoryName, String manufacturerName, Long categoryId, Long manufacturerId) {
        super(id);
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.color = color;
        this.screenSize = screenSize;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.image = new ProductImageDTO(image.getId(), image.getPublicId(), image.getFileName(), image.getUrl());
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

}
