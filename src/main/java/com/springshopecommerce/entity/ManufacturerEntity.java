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

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "publicId")
    private String publicId;


    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

}
