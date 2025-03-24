package org.project.articleservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.articleservice.dto.CommentRequestDto;
import org.project.articleservice.dto.CommentResponseDto;
import org.project.articleservice.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public String saveComment(CommentRequestDto requestDto) {
        log.info("CommentController :: saveComment :: requestDto = {}", requestDto);

        CommentResponseDto comment = commentService.saveComment(requestDto);

        return "redirect:/articles/" + comment.article().getId();
    }

}
