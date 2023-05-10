package com.springshopecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<UserEntity> users = new ArrayList<>();

  public RoleEntity() {
  }

  public RoleEntity(String name, List<UserEntity> users) {
    this.name = name;
    this.users = users;
  }

  public RoleEntity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(List<UserEntity> users) {
    this.users = users;
  }

  public void addUser(UserEntity user) {
    this.users.add(user);
    user.getRoles().add(this);
  }

  public void removeUser(UserEntity user) {
    this.users.remove(user);
    user.getRoles().remove(this);
  }

  public void removeUser() {
    for (UserEntity user : new ArrayList<>(users)) {
      removeUser(user);
    }
  }
}
