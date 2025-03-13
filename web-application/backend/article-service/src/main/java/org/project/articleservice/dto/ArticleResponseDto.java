package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;

import java.time.LocalDateTime;

public record ArticleResponseDto(Long id, String title, String content, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate) {

    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedBy(),
                article.getCreatedDate(),
                article.getModifiedBy(),
                article.getModifiedDate()
        );
    }

}
