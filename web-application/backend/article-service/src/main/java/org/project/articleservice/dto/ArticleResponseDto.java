package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ArticleResponseDto(Long id, String title, String content, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, List<AttachmentResponseDto> attachments) {

    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedBy(),
                article.getCreatedDate(),
                article.getModifiedBy(),
                article.getModifiedDate(),
                article.getAttachments().stream().map(AttachmentResponseDto::from).collect(Collectors.toList())
        );
    }

}
