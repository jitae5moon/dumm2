package org.project.articleservice.service;

import org.project.articleservice.dto.ArticleResponseDto;
import org.project.articleservice.dto.ArticleSaveRequestDto;
import org.project.articleservice.dto.ArticleUpdateRequestDto;

import java.util.List;

public interface ArticleService {

    List<ArticleResponseDto> getArticles();

    ArticleResponseDto getArticle(Long id);

    ArticleResponseDto saveArticle(ArticleSaveRequestDto dto);

    ArticleResponseDto updateArticle(Long id, ArticleUpdateRequestDto dto);

    void deleteArticle(Long id);

}
