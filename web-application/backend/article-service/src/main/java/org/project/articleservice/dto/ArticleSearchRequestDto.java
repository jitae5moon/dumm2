package org.project.articleservice.dto;

public record ArticleSearchRequestDto(
        String searchType,
        String searchWord,
        Integer currentPage,
        Integer pageSize,
        Integer offset
) {

    public ArticleSearchRequestDto {
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
        offset = (currentPage - 1) * pageSize;
    }

}
