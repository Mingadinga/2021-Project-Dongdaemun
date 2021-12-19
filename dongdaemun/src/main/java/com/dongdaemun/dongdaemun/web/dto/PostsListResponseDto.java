package com.dongdaemun.dongdaemun.web.dto;

import com.dongdaemun.dongdaemun.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String uid;
    private String title;
    private String content;
    private boolean anony;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.uid = entity.getUid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.anony = entity.isAnony();
        this.modifiedDate = entity.getModifieDate();
    }
}
