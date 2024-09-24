package com.sboard.service;

import com.querydsl.core.Tuple;
import com.sboard.dto.PageRequestDTO;
import com.sboard.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void selectAllArticles() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        Pageable pageable = pageRequestDTO.getPageable("no");

        // 엔티티 조회
        // List<Article> articles = articleRepository.findAll();
        Page<Tuple> pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable);

        System.out.println(pageArticle.getContent());
    }
}