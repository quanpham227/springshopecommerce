package com.springshopecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity extends AbstractEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(nullable = false)
    private double amount;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "status")
    private OrdersStatus status;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails;

    @PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }




}
