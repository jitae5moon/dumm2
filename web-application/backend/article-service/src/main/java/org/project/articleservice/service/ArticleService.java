package org.project.articleservice.service;

import org.project.articleservice.dto.*;
import org.springframework.data.domain.Page;

public interface ArticleService {

    Page<ArticleResponseDto> getArticles(ArticleSearchRequestDto searchRequestDto);

    ArticleResponseDto getArticle(Long id);

    ArticleResponseDto saveArticle(ArticleSaveRequestDto dto);

    ArticleResponseDto updateArticle(Long id, ArticleUpdateRequestDto dto);

    void deleteArticle(Long id);

}
