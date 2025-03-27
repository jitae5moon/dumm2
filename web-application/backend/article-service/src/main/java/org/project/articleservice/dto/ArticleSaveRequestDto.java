package org.project.articleservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.articleservice.domain.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class ArticleSaveRequestDto {

    private String title;
    private String content;
    private Long parentId;
    private List<MultipartFile> attachments;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}
