package com.example.Test.Article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String List(Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);

        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";

    }

    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }

    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bingingResult) {
        this.articleService.create(articleForm.getSubject(), articleForm.getContent());
        return "redirect:/article/list";
    }
    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        return "article_form";

    }
    @PostMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Integer id, ArticleForm articleForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);

        if(bindingResult.hasErrors()){
            return "article_form";
        }
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());

        return String.format("redirect:/article/detail/%s",article.getId());
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);

        this.articleService.delete(article);
        return "redirect:/";

    }


}
