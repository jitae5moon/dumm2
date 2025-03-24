package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;
import org.project.articleservice.domain.Comment;

public record CommentRequestDto(String content, Long articleId) {

    public Comment toEntity(Article article) {
        return Comment.builder()
                .content(content)
                .article(article)
                .build();
    }

}
