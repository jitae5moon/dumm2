package org.project.articleservice.dto;

public record ArticleSearchRequestDto(
        String searchType,
        String searchWord
) {
}
