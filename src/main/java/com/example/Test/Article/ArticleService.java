package com.example.Test.article;

import com.example.Test.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);

        return article.get();
    }

    public void create(String subject, String content, Member member, Boolean pinned) {
        Article a = new Article();
        a.setSubject(subject);
        a.setContent(content);
        a.setCreateDate(LocalDateTime.now());
        a.setAuthor(member);
        a.setPinned(pinned);
        this.articleRepository.save(a);
    }

    public void modify(Article a, String subject, String content,Boolean pinned) {
        a.setSubject(subject);
        a.setContent(content);
        a.setModifyDate(LocalDateTime.now());
        a.setPinned(pinned);
        this.articleRepository.save(a);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public Page<Article> getList(int page, String kw) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("pinned").descending().and(Sort.by("createDate").descending()));
        Specification<Article> spec = search(kw);

        return this.articleRepository.findAll(spec, pageable);
    }


    private Specification<Article> search(String kw) {
        return new Specification<>() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Article> a, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);


                return cb.or(cb.like(a.get("subject"), "%" + kw + "%"), cb.like(a.get("content"), "%" + kw + "%"));
            }
        };
    }

    public void vote(Article article, Member member) {
        article.getVoter().add(member);
        this.articleRepository.save(article);
    }

    public void viewCount(Article article) {
        Integer viewCount = article.getViewCount();

        if (viewCount == null) {
            viewCount = 0;
        }
        int viewCountInt = viewCount;
        article.setViewCount(viewCountInt + 1);
        this.articleRepository.save(article);

    }

}