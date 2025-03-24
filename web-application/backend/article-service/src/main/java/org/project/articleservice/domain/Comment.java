package org.project.articleservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Comment extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 1000)
    private String content;

    @Setter
    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    protected Comment() { }

    @Builder
    public Comment(Long id, String content, Article article) {
        this.id = id;
        this.content = content;
        this.article = article;
    }

}
