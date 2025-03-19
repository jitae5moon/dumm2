package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Article;
import org.project.articleservice.dto.ArticleResponseDto;
import org.project.articleservice.dto.ArticleSaveRequestDto;
import org.project.articleservice.dto.ArticleSearchRequestDto;
import org.project.articleservice.dto.ArticleUpdateRequestDto;
import org.project.articleservice.repository.ArticleRepository;
import org.project.articleservice.repository.specification.ArticleSpecification;
import org.springframework.data.jpa.domain.Specification;
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
    public List<ArticleResponseDto> getArticles(ArticleSearchRequestDto dto) {
        Specification<Article> specification = Specification.where(null);

        if (dto.searchType() != null) {
            switch (dto.searchType()) {
                case "title":
                    specification = specification.and(ArticleSpecification.likeTitle(dto.searchWord().trim()));
                    break;
                case "content":
                    specification = specification.and(ArticleSpecification.likeContent(dto.searchWord().trim()));
                    break;
                case "createdBy":
                    specification = specification.and(ArticleSpecification.likeCreatedBy(dto.searchWord().trim()));
                    break;
            }
        }

        List<Article> articles = articleRepository.findAll(specification);

        return articles.stream()
                .map(article -> ArticleResponseDto.from(article))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDto getArticle(Long id) {
        return ArticleResponseDto.from(articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id)));
    }

    @Override
    public ArticleResponseDto saveArticle(ArticleSaveRequestDto dto) {
        Article savedArticle = articleRepository.save(dto.toEntity());

        return ArticleResponseDto.from(savedArticle);
    }

    @Override
    public ArticleResponseDto updateArticle(Long id, ArticleUpdateRequestDto dto) {
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
