package com.example.Test.article;

import com.example.Test.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<Member> voter;

    @Column
    private Integer viewCount = 0;

    @Column
    private Boolean pinned = false;


}
