package com.springshopecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO{

    private Long productId;
    private String name;
    private int quantity;
    private double unitPrice;
}
