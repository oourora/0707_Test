package com.example.Test.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article findBySubject(String subject);
    Article findBySubjectAndContent(String subject, String content);
    Page<Article> findAll(Pageable pageable);
//    Page<Article> findBySubject(String kw);
    Page<Article> findAll(Specification<Article> spec, Pageable pageable);


}
