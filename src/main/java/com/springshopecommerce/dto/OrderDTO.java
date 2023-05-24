package com.springshopecommerce.dto;

import com.springshopecommerce.entity.CustomerEntity;
import com.springshopecommerce.entity.OrderDetailEntity;
import com.springshopecommerce.entity.OrdersStatus;
import com.springshopecommerce.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO extends AbstractDTO<OrderDTO>{

    private Date shippingDate;

    private String shippingAddress;

    private double amount;

    private PaymentMethod paymentMethod;

    private OrdersStatus status;

    private Date createDate;

    private Date updateDate;

    private CustomerEntity customer;

    private List<OrderDetailEntity> orderDetails;

}
