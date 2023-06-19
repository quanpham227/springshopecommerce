package com.springshopecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO implements Serializable {

    private Long productId;
    private String name;
    private int quantity;
    private double unitPrice;
    private String image;
}
