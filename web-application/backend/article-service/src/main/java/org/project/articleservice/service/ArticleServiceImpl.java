package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Article;
import org.project.articleservice.dto.*;
import org.project.articleservice.repository.ArticleRepository;
import org.project.articleservice.repository.specification.ArticleSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final AttachmentService attachmentService;
    private final ArticleRepository articleRepository;

    @Override
    public Page<ArticleResponseDto> getArticles(ArticleSearchRequestDto searchRequestDto) {
        Pageable pageable = PageRequest.of(searchRequestDto.currentPage() - 1, searchRequestDto.pageSize(), Sort.by("createdDate").descending());

        Specification<Article> specification = Specification.where(null);

        if (searchRequestDto.searchType() != null) {
            switch (searchRequestDto.searchType()) {
                case "title":
                    specification = specification.and(ArticleSpecification.likeTitle(searchRequestDto.searchWord().trim()));
                    break;
                case "content":
                    specification = specification.and(ArticleSpecification.likeContent(searchRequestDto.searchWord().trim()));
                    break;
                case "createdBy":
                    specification = specification.and(ArticleSpecification.likeCreatedBy(searchRequestDto.searchWord().trim()));
                    break;
            }
        }

        Page<Article> articles = articleRepository.findAll(specification, pageable);

        return articles.map(article -> ArticleResponseDto.from(article));
    }

    @Override
    public ArticleResponseDto getArticle(Long id) {
        return ArticleResponseDto.from(articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + id)));
    }

    @Override
    public ArticleResponseDto saveArticle(ArticleSaveRequestDto dto) {
        Article savedArticle = articleRepository.save(dto.toEntity());

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
