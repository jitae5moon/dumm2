package org.project.articleservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Attachment extends AuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String path;

    @Setter
    private Long size;

    @Setter
    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    protected Attachment() { }

    @Builder
    public Attachment(Long id, String name, String path, Long size, Article article) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.article = article;
    }

}
