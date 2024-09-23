package com.sboard.controller;

import com.sboard.Service.ArticleService;
import com.sboard.Service.FileService;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    @GetMapping("/list")
    public String list(Model model) {
        return "/article/list";
    }

    @GetMapping("/view")
    public String view() {
        return "/article/view";
    }

    @GetMapping("/modify")
    public String modify() {
        return "/article/modify";
    }

    @GetMapping("/write")
    public String write(Model model) {
        return "/article/write";
    }

    @PostMapping("/write")
    public String write(ArticleDTO articleDTO, HttpServletRequest request) {

        String regip = request.getRemoteAddr();
        articleDTO.setRegip(regip);
        log.info(articleDTO);

        // 파일 업로드
        List<FileDTO> uploadedFiles = fileService.uploadFile(articleDTO);

        // 글 저장
        articleDTO.setFile(uploadedFiles.size()); // 첨부 파일 갯수 초기화
        int ano = articleService.insertArticle(articleDTO);

        // 파일 저장
        for (FileDTO fileDTO : uploadedFiles) {
            fileDTO.setAno(ano);
            fileService.insertFile(fileDTO);
        }


        return "redirect:/article/list";
    }

}
