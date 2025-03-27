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
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final AttachmentService attachmentService;
    private final ArticleRepository articleRepository;

    @Override
    public Page<ArticleResponseDto> getArticles(ArticleSearchRequestDto searchRequestDto) {
        Pageable pageable = PageRequest.of(searchRequestDto.currentPage() - 1, searchRequestDto.pageSize(), Sort.by("createdDate").descending());

        Specification<Article> spec = Specification.where(ArticleSpecification.parentIsNull());

        if (searchRequestDto.searchType() != null) {
            switch (searchRequestDto.searchType()) {
                case "title":
                    spec = spec.and(ArticleSpecification.likeTitle(searchRequestDto.searchWord().trim()));
                    break;
                case "content":
                    spec = spec.and(ArticleSpecification.likeContent(searchRequestDto.searchWord().trim()));
                    break;
                case "createdBy":
                    spec = spec.and(ArticleSpecification.likeCreatedBy(searchRequestDto.searchWord().trim()));
                    break;
            }
        }

        Page<Article> articles = articleRepository.findAll(spec, pageable);
        List<Article> result = new ArrayList<>();

        for (Article article : articles.getContent()) {
            result.add(article);
            for (Article child : article.getChildren()) {
                result.add(child);
            }
        }

        PageImpl<ArticleResponseDto> articleResponseDtos = new PageImpl<>(result.stream().map(ArticleResponseDto::from).collect(Collectors.toList()), pageable, articles.getTotalPages());

        return articleResponseDtos;
    }

    @Override
    public ArticleResponseDto getArticle(Long id) {
        return ArticleResponseDto.from(articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id)));
    }

    @Override
    public ArticleResponseDto saveArticle(ArticleSaveRequestDto dto) {
        Article article = dto.toEntity();

        if (dto.getParentId() != null) {
            Article parentArticle = articleRepository.findById(dto.getParentId()).orElseThrow(() -> new EntityNotFoundException("Parent not found, id = " + dto.getParentId()));
            article.setParent(parentArticle);
            article.setDepth(parentArticle.getDepth() + 1);
        }

        Article savedArticle = articleRepository.save(article);

        if (dto.getAttachments() != null) {
            dto.getAttachments().forEach(attachment -> {
                attachmentService.upload(attachment, savedArticle);
            });
        }

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
