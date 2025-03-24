package org.project.articleservice.dto;

public record PaginationDto(
        Integer currentPage,
        Integer pageSize
) {

}
