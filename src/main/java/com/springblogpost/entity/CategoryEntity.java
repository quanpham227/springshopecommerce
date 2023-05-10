package com.springblogpost.entity;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    private String name;



    @OneToMany(mappedBy = "category")
    private List<PostEntity> posts = new ArrayList<>();


    public CategoryEntity() {
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public void addPost(PostEntity postEntity) {
        postEntity.setCategory(this);
        this.posts.add(postEntity);
    }

    public void removePost(PostEntity postEntity) {
        this.posts.remove(postEntity);
        postEntity.setCategory(null);

    }
}
