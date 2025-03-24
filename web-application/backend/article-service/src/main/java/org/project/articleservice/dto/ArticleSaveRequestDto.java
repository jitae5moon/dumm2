package org.project.articleservice.dto;

import org.project.articleservice.domain.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ArticleSaveRequestDto(String title, String content, List<MultipartFile> files) {

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}
