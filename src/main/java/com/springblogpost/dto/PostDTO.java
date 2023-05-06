package com.springblogpost.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO extends AbstractDTO<PostDTO> {

  @NotBlank(message = "title should not be blank")
  @Size(min = 3, message = "title should be at least 3 chars")
  private String title;

  @NotBlank(message = "shortDescription should not be blank")
  @Size(min = 3, message = "shortDescription should be at least 3 chars")
  private String shortDescription;

  @NotBlank(message = "content should not be blank")
  @Size(min = 3, message = "content should be at least 3 chars")
  private String content;

  private Long categoryId;

  private CategoryDTO category;

  private String categoryName;

  private List<CommentDTO> comments;

  private List<PostImageDTO> images;


}
