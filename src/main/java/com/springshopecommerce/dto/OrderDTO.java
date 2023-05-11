package com.springshopecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends AbstractDTO<OrderDTO>{

    private Date oderDate;
    private Long customerId;
    private double amount;
    private short status;
}
