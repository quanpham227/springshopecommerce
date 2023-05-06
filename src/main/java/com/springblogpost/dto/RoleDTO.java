package com.springblogpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class RoleDTO extends AbstractDTO<UserDTO> {

  @NotBlank(message = "Name should not be blank")
  @Size(min = 6, message = "Name should be at least 6 chars")
  private String name;

  @JsonIgnore
  private List<UserDTO> users = new ArrayList<>();

  public RoleDTO() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<UserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<UserDTO> users) {
    this.users = users;
  }
}
