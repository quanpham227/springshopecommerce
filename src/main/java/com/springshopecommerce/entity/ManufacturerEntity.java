package com.springshopecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "publicId")
    private String publicId;


    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();


    public ManufacturerEntity(Long id, String name, String logoUrl, String publicId, String fileName) {
        super(id);
        this.name = name;
        this.logoUrl = logoUrl;
        this.publicId = publicId;
        this.fileName = fileName;
    }
}
