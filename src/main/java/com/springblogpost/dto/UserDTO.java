package com.springblogpost.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.springblogpost.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO extends AbstractDTO<UserDTO> implements Serializable {

  @NotEmpty
  @Size(min = 3, message = "password should have at least 3 characters")
  private String userName;

  @NotEmpty
  @Size(min = 8, message = "password should have at least 8 characters")
  private String password;

  @NotEmpty
  @Size(min = 5, message = "user name should have at least 5 characters")
  private String fullName;

  private UserStatus status;

  private List<RoleDTO> roles = new ArrayList<>();
}
