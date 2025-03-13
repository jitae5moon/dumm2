package org.project.articleservice.repository;

import org.junit.jupiter.api.Test;
import org.project.articleservice.config.JpaConfig;
import org.project.articleservice.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaConfig.class)
@DataJpaTest
class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    @Autowired
    public ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Test
    void testJpaAudit() {
        // Given
        Article article = createArticle();

        // When
        articleRepository.save(article);

        // Then
        Article savedArticle = articleRepository.findById(1L).orElseThrow();
        assertThat(savedArticle.getCreatedDate()).isNotNull();
    }

    Article createArticle() {
        Article article = Article.builder()
                .title("Test title")
                .content("Test content")
                .build();

        return article;
    }

}