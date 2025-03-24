package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Article;
import org.project.articleservice.domain.Comment;
import org.project.articleservice.dto.CommentRequestDto;
import org.project.articleservice.dto.CommentResponseDto;
import org.project.articleservice.repository.ArticleRepository;
import org.project.articleservice.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto saveComment(CommentRequestDto requestDto) {
        Long articleId = requestDto.articleId();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("Article not found, id = " + articleId));

        Comment comment = requestDto.toEntity(article);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.from(savedComment);
    }

}
