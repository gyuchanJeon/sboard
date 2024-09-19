package com.sboard.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sboard.Service.TermsService;
import com.sboard.Service.UserService;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

@Log4j2
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TermsService termsService;
    private final HttpServletResponse httpServletResponse;
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("uid") String uid, @RequestParam("pass") String pass) {
        UserDTO userDTO = userService.selectUser(uid);
        if(userDTO != null) {
            if(pass.equals(userDTO.getPass())) {
                return "/article/list";
            }
        }
        return "/user/login?success=101";
    }


    @GetMapping("/register")
    public String register(Model model) {
        List<UserDTO> userDTOs = userService.selectUsers();
        model.addAttribute("users", userDTOs);
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(UserDTO userDTO) {
        userService.insertUser(userDTO);
        return "/user/login";
    }


    @GetMapping("/terms")
    public String terms(Model model) {
        List<TermsDTO> termsDTOs = termsService.selectTerms();
        model.addAttribute("terms", termsDTOs);
        return "/user/terms";
    }

    @GetMapping("/terms/json")
    @ResponseBody
    public List<TermsDTO> getTermsJson() {
        return termsService.selectTerms();
    }

    @ResponseBody
    @GetMapping("/checkUser")
    public boolean checkUser(@RequestParam("type") String type, @RequestParam("value") String value, Model model) throws IOException {
        boolean result = userService.checkUid("email", value);
        if(type.equals("email") && result == false) {
            // 이메일 인증번호 발송하기
            String code = userService.sendEmailCode(value);

            // 세션 저장
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("authCode", code);
        }

        // JSON 생성
        JsonObject json = new JsonObject();
        json.addProperty("result", result);

        // JSON 출력
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(json);
        writer.flush();
        writer.close();
        return userService.checkUid(type, value);
    }


    @ResponseBody
    @PostMapping("/checkUser")
    public void checkUser() throws IOException {
        // Javascript fetch함수 POST JSON 문자열 스트림 처리
        BufferedReader reader = httpServletRequest.getReader();
        StringBuilder requestBody = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null){
            requestBody.append(line);
        }
        reader.close();

        // JSON 파싱
        Gson gson = new Gson();
        Properties prop = gson.fromJson(requestBody.toString(), Properties.class);
        String code = prop.getProperty("code");
        log.debug("code : " + code);

        // 인증코드 일치 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String authCode = (String) session.getAttribute("authCode");
        log.debug("authCode : " + authCode);

        // JSON 생성 후 출력
        JsonObject json = new JsonObject();

        if(authCode.equals(code)) {
            json.addProperty("result", 1);
        }else {
            json.addProperty("result", 0);
        }

        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(json);
        writer.flush();
        writer.close();
    }


}
