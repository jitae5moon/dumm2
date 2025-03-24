package org.project.articleservice.service;

import org.project.articleservice.domain.Article;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface AttachmentService {

    void upload(MultipartFile file, Article article);

    Resource download(Long id) throws MalformedURLException;

}
