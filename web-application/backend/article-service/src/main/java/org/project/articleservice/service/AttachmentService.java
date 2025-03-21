package org.project.articleservice.service;

import org.project.articleservice.domain.Article;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    public void upload(MultipartFile file, Article article);

}
