package com.example.Test.Article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String List(Model model){
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList",articleList);

        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ArticleForm articleForm){
//        Article article = this.articleService.getArticle();
//        model.addAttribute("article", article);
        return "article_detail";

    }
//
//    @GetMapping("/create")
//    public String create(){
//        return "article_form";
//    }
//
//    @PostMapping("/create")
//    public String create(){
//        return "redirect:/article/list";
//    }



}