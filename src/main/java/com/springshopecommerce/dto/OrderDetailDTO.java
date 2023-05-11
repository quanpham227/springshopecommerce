package com.springshopecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO extends AbstractDTO<OrderDetailDTO> {
    private Long orderId;
    private Long productId;
    private int quantity;
    private double unitPrice;
}
