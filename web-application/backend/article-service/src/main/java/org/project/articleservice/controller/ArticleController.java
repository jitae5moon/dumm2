package org.project.articleservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.articleservice.dto.*;
import org.project.articleservice.service.ArticleService;
import org.project.articleservice.service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping("/save")
    public String getViewForSaveArticle(Model model) {
        log.info("ArticleController :: getViewForSaveArticle");

        return "articles/save";
    }

    // TODO: Add validation
    @GetMapping
    public String getArticles(ArticleSearchRequestDto searchRequestDto, Model model) {
        log.info("ArticleController :: getArticles ");
        Page<ArticleResponseDto> articles = articleService.getArticles(searchRequestDto);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(searchRequestDto.currentPage(), articles.getTotalPages());

        model.addAttribute("articles", articles.getContent());
        model.addAttribute("searchRequestDto", searchRequestDto);
        model.addAttribute("totalPages", articles.getTotalPages());
        model.addAttribute("paginationBarNumbers", paginationBarNumbers);

        return "articles/list";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        log.info("ArticleController :: getArticle :: id = {}", id);
        ArticleResponseDto article = articleService.getArticle(id);

        model.addAttribute("article", article);

        return "articles/detail";
    }

    @PostMapping
    public String saveArticle(ArticleSaveRequestDto dto, Model model) {
        log.info("ArticleController :: saveArticle :: dto = {}", dto);
        dto.setAttachments(dto.getAttachments().stream().filter(attachment -> !attachment.isEmpty()).collect(Collectors.toList()));
        articleService.saveArticle(dto);

        return "redirect:/articles";
    }

    @PutMapping("/{id}")
    public String updateArticle(@PathVariable Long id, ArticleUpdateRequestDto dto, Model model) {
        log.info("ArticleController :: upddateArticle :: dto = {}", dto);
        articleService.updateArticle(id, dto);

        return "articles/detail";
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable Long id) {
        log.info("ArticleController :: deleteArticle :: id = {}", id);
        articleService.deleteArticle(id);

        return "articles/list";
    }

}
