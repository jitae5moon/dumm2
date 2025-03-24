package org.project.articleservice.service;

import org.project.articleservice.dto.CommentRequestDto;
import org.project.articleservice.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto saveComment(CommentRequestDto requestDto);

}
