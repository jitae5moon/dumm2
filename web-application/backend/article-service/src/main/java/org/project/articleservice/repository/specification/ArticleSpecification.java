package org.project.articleservice.repository.specification;

import org.project.articleservice.domain.Article;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

    public static Specification<Article> likeTitle(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
    }

    public static Specification<Article> likeContent(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
    }

    public static Specification<Article> likeCreatedBy(String keyword) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("createdBy"), "%" + keyword + "%"));
    }

    public static Specification<Article> parentIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parent"));
    }

}
