package com.frunoman.load.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostBody extends BaseJson{
    private String title;
    private String body;
    private Integer userId;


    public static PostBody generatePostBody(){
        PostBody postBody = new PostBody();
        postBody.setTitle("foo");
        postBody.setBody("bar");
        postBody.setUserId(1);
        return postBody;
    }

    @Override
    public ObjectMapper objectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }
}
