package com.example.Test.article;

import com.example.Test.member.Member;
import com.example.Test.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String List(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Article> paging = this.articleService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        articleService.viewCount(article);
        model.addAttribute("article", article);
        return "article_detail";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bingingResult, Principal principal) {
        if (bingingResult.hasErrors()) {
            return "article_form";
        }
        Member member = this.memberService.getUser(principal.getName());
        this.articleService.create(articleForm.getSubject(), articleForm.getContent(), member, articleForm.getPinned());
        return "redirect:/article/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, ArticleForm articleForm, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        articleForm.setPinned(article.getPinned());
        return "article_form";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String Modify(@Valid ArticleForm articleForm, BindingResult bindingResult,
                         Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent(),articleForm.getPinned());
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
		if (!article.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다");
        }
        this.articleService.delete(article);
        return "redirect:/";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String Vote(Principal principal, @PathVariable("id") Integer id){
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getUser(principal.getName());
        this.articleService.vote(article,member);
        return String.format("redirect:/article/detail/%s", id);
    }


}
