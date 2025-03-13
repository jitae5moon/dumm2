package org.project.articleservice.service;

import org.project.articleservice.dto.ArticleResponseDto;
import org.project.articleservice.dto.ArticleSaveDto;
import org.project.articleservice.dto.ArticleUpdateDto;

import java.util.List;

public interface ArticleService {

    List<ArticleResponseDto> getArticles();

    ArticleResponseDto getArticle(Long id);

    ArticleResponseDto saveArticle(ArticleSaveDto dto);

    ArticleResponseDto updateArticle(Long id, ArticleUpdateDto dto);

    void deleteArticle(Long id);

}
