package org.project.articleservice.dto;

import lombok.Builder;
import org.project.articleservice.domain.Attachment;

public record AttachmentResponseDto(
        Long id,
        String name,
        String path,
        Long size
) {

    @Builder
    public AttachmentResponseDto(Long id, String name, String path, Long size) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
    }

    public static AttachmentResponseDto from(Attachment attachment) {
        return AttachmentResponseDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .path(attachment.getPath())
                .size(attachment.getSize())
                .build();
    }

}
