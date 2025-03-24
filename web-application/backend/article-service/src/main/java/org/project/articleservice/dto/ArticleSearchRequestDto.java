package org.project.articleservice.dto;

public record ArticleSearchRequestDto(
        String searchType,
        String searchWord,
        Integer currentPage,
        Integer pageSize
) {

    public ArticleSearchRequestDto {
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
    }

}
