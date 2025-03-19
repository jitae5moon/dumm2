package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;

public record ArticleUpdateRequestDto(String title, String content) {

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}
