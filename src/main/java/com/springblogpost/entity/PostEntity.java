package com.springblogpost.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {

  @Column(name = "title")
  private String title;

  @Column(name = "shortdescription", columnDefinition = "TEXT")
  private String shortDescription;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private CategoryEntity category;

  @OneToMany(mappedBy = "post", orphanRemoval = true)
  private List<CommentEntity> comments = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "post_post_image",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "post_image_id"))
  private Set<PostImageEntity> images = new LinkedHashSet<>();

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "post_image_id")
  private PostImageEntity image;

}
