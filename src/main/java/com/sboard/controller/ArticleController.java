package com.sboard.controller;

import com.sboard.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, HttpSession httpSession) {
        String user = (String) httpSession.getAttribute("user");
        model.addAttribute("user", user);
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
