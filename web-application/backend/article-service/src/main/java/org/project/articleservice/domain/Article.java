package org.project.articleservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Article extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 500)
    private String title;

    @Setter
    @Column(length = 5000)
    private String content;

    @OneToMany(mappedBy = "article")
    private List<Attachment> attachments = new ArrayList<>();

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    protected Article() {}

    @Builder
    public Article(Long id, String title, String content, List<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attachments = attachments;
    }

}
