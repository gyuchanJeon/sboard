package com.sboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

    @GetMapping("/list")
    public String list() {
        return "/list";
    }

    @GetMapping("/view")
    public String view() {
        return "/view";
    }

    @GetMapping("/modify")
    public String modify() {
        return "/modify";
    }

    @GetMapping("/write")
    public String write() {
        return "/write";
    }

}
