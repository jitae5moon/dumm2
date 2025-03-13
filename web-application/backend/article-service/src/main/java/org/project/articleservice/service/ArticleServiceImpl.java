package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Article;
import org.project.articleservice.dto.ArticleResponseDto;
import org.project.articleservice.dto.ArticleSaveDto;
import org.project.articleservice.dto.ArticleUpdateDto;
import org.project.articleservice.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public List<ArticleResponseDto> getArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(article -> ArticleResponseDto.from(article))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDto getArticle(Long id) {
        return ArticleResponseDto.from(articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id)));
    }

    @Override
    public ArticleResponseDto saveArticle(ArticleSaveDto dto) {
        Article savedArticle = articleRepository.save(dto.toEntity());

        return ArticleResponseDto.from(savedArticle);
    }

    @Override
    public ArticleResponseDto updateArticle(Long id, ArticleUpdateDto dto) {
        Article savedArticle = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id));

        if (dto.title() != null) savedArticle.setTitle(dto.title());
        if (dto.content() != null) savedArticle.setContent(dto.content());

        return ArticleResponseDto.from(savedArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id));

        articleRepository.delete(article);
    }


}
