package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;
import org.project.articleservice.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        String content,
        String createdBy,
        LocalDateTime createdDate,
        Article article
) {

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(comment.getId(),
                comment.getContent(),
                comment.getCreatedBy(),
                comment.getCreatedDate(),
                comment.getArticle());
    }

}
