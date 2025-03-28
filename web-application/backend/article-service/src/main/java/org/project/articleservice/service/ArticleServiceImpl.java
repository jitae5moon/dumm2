package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Article;
import org.project.articleservice.dto.ArticleResponseDto;
import org.project.articleservice.dto.ArticleSaveRequestDto;
import org.project.articleservice.dto.ArticleSearchRequestDto;
import org.project.articleservice.dto.ArticleUpdateRequestDto;
import org.project.articleservice.repository.mapper.ArticleMapper;
import org.project.articleservice.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final AttachmentService attachmentService;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public int countArticles(ArticleSearchRequestDto searchRequestDto) {
        return articleMapper.countArticles(searchRequestDto);
    }

    @Override
    public List<ArticleResponseDto> getArticles(ArticleSearchRequestDto searchRequestDto) {
        List<Article> articles = articleMapper.selectArticles(searchRequestDto);

        return articles.stream().map(ArticleResponseDto::from).collect(Collectors.toList());
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
