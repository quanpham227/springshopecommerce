package com.springshopecommerce.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO extends AbstractDTO<CategoryDTO> implements Serializable {

  @NotEmpty
  @Length(min = 5)
  private String name;

  private Boolean isEdit = false;
}