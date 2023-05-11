package com.springshopecommerce.dto;

import com.springshopecommerce.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends AbstractDTO<CustomerDTO> implements Serializable {

    private String name;

    private String email;

    private String password;

    private String phone;

    private Date registereDate;

    private short status;

    private List<OrderEntity> orders = new ArrayList<>();

}
