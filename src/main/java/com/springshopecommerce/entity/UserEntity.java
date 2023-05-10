package com.springshopecommerce.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

  @Column(name = "username")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "fullname")
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private UserStatus status;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "userid"),
      inverseJoinColumns = @JoinColumn(name = "roleid")
  )
  private List<RoleEntity> roles = new ArrayList<>();

  public UserEntity() {
  }

  public UserEntity(String userName, String password, String fullName, UserStatus status,
      List<RoleEntity> roles) {
    this.userName = userName;
    this.password = password;
    this.fullName = fullName;
    this.status = status;
    this.roles = roles;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public List<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleEntity> roles) {
    this.roles = roles;
  }

  public void addRole(RoleEntity role) {
    this.roles.add(role);
    role.getUsers().add(this);
  }

  public void removeRole(RoleEntity role) {
    this.roles.remove(role);
    role.getUsers().remove(this);
  }

  public void removeRole() {
    for (RoleEntity role : new ArrayList<>(roles)) {
      removeRole(role);
    }
  }

  @Override
  public String toString() {
    return "UserEntity{" +
        "userName='" + userName + '\'' +
        ", password='" + password + '\'' +
        ", fullName='" + fullName + '\'' +
        ", status=" + status +
        ", roles=" + roles +
        '}';
  }
}