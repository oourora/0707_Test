package com.example.Test.Article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {

    @NotEmpty(message = "제목은 필수입니다")
    private String Subject;

    @NotEmpty(message = "내용은 필수입니다")
    private String Content;

}
