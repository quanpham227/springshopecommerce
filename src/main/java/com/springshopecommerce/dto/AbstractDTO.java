package com.springshopecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDTO<T> {

  private Long id;
  @JsonIgnore
  private Timestamp createdDate;
  @JsonIgnore
  private Timestamp modifiedDate;
  @JsonIgnore
  private String createdBy;
  @JsonIgnore
  private String modifiedBy;
}
