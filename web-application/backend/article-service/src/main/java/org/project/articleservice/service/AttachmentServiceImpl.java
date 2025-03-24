package org.project.articleservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.articleservice.domain.Article;
import org.project.articleservice.domain.Attachment;
import org.project.articleservice.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void upload(MultipartFile file, Article article) {
        Path path = Paths.get(uploadPath, file.getOriginalFilename());

        try {
            file.transferTo(path);
            Attachment attachment = Attachment.builder()
                    .name(file.getOriginalFilename())
                    .path(path.toString())
                    .size(file.getSize())
                    .article(article)
                    .build();

            if (article.getAttachments() == null) article.setAttachments(new ArrayList<>());
            article.addAttachment(attachment);
            attachmentRepository.save(attachment);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource download(Long id) throws MalformedURLException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attachment not found, id = " + id));
        Path path = Paths.get(attachment.getPath());

        return new UrlResource(path.toUri());
    }

}
