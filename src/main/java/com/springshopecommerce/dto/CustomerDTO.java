package com.springshopecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends AbstractDTO<CustomerDTO> implements Serializable {
    private String name;
    private String email;
    private String password;
    private String phone;
    private short status;
}
