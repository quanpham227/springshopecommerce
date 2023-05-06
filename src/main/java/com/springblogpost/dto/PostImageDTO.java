package com.springblogpost.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostImageDTO  extends AbstractDTO<PostImageDTO> implements Serializable {

    private String name;

    private String uid;

    private String fileName;

    private String url;

    private String status;

}
