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

    @Setter
    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Article parent;

    @Setter
    @OneToMany(mappedBy = "parent")
    private List<Article> children = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "article")
    private List<Attachment> attachments = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    @Setter
    private Integer depth;

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    protected Article() {}

    @Builder
    public Article(Long id, String title, String content, Article parent, List<Article> children, List<Attachment> attachments, List<Comment> comments, int depth) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.parent = parent;
        this.children = children;
        this.attachments = attachments;
        this.comments = comments;
        this.depth = depth;
    }

}
