package com.springshopecommerce.dto;
import com.springshopecommerce.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO extends AbstractDTO<CategoryDTO> implements Serializable {

  @NotEmpty
  @Length(min = 5)
  private String name;

  private List<ProductDTO> products = new ArrayList<>();

  private Boolean isEdit = false;

  public CategoryDTO(String name) {
    this.name = name;
  }
  public CategoryDTO(Long id, String name) {
    super(id);
    this.name = name;
  }
  public CategoryDTO(Long id, String name, Boolean isEdit) {
    super(id);
    this.name = name;
    this.isEdit = isEdit;
  }
}
