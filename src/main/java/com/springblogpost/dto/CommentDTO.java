package com.springblogpost.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO extends AbstractDTO<CommentDTO>implements Serializable {

  @NotBlank(message = "content should not be blank")
  @Size(min = 3, max = 100, message = "Content must be at least 3 characters, up to 100 characters")
  private String content;
  @NotNull(message = "post id not be null")
  private Long postId;
}
