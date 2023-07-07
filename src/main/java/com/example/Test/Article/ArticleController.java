package com.example.Test.Article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public String List(Model model){


        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model){
        return "article_detail";
    }



}
