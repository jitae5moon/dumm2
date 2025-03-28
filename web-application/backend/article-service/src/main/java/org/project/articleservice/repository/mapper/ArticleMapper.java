package org.project.articleservice.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.project.articleservice.domain.Article;
import org.project.articleservice.dto.ArticleSearchRequestDto;

import java.util.List;

@Mapper
public interface ArticleMapper {

    int countArticles(ArticleSearchRequestDto searchRequestDto);

    List<Article> selectArticles(ArticleSearchRequestDto requestDto);

}
