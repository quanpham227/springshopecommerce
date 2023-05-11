package com.springshopecommerce.dto;

import com.springshopecommerce.entity.OrderEntity;
import com.springshopecommerce.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO extends AbstractDTO<OrderDetailDTO> {
    private Long orderId;

    private int quantity;

    private double unitPrice;

    private ProductEntity product;

    private OrderEntity order;
}
