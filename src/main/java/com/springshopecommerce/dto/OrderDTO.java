package com.springshopecommerce.dto;

import com.springshopecommerce.entity.CustomerEntity;
import com.springshopecommerce.entity.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO extends AbstractDTO<OrderDTO>{
    private Date orderDate;

    private double amount;

    private short status;

    private CustomerEntity customer;

    private List<OrderDetailEntity> orderDetails;
}
