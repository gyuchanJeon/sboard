package com.sboard.controller;

import com.google.gson.Gson;
import com.sboard.Service.TermsService;
import com.sboard.Service.UserService;
import com.sboard.config.AppInfo;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TermsService termsService;
    private final HttpServletRequest httpServletRequest;
    private final AppInfo appInfo;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(appInfo);
        return "/user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(UserDTO userDTO) {
        String regip = httpServletRequest.getRemoteAddr();
        userDTO.setRegip(regip);
        userService.insertUser(userDTO);
        return "redirect:/user/login?success=102";
    }

    @GetMapping("/terms")
    public String terms(Model model) {
        List<TermsDTO> termsDTOs = termsService.selectTerms();
        model.addAttribute("terms", termsDTOs);
        return "/user/terms";
    }

    @GetMapping("/terms/json") // 이용약관 json 데이터 전송을 위한 URI mapping
    @ResponseBody
    public List<TermsDTO> getTermsJson() {
        return termsService.selectTerms();
    }

    @ResponseBody
    @GetMapping("/checkUser")
    public ResponseEntity checkUser(@RequestParam("type") String type, @RequestParam("value") String value, HttpSession session) throws IOException {
        boolean result = userService.checkUid("email", value);

        // 타입이 "email"이고 UID가 존재하지 않을 경우 이메일 인증 코드 발송
        if ("email".equalsIgnoreCase(type) && !result) {
            String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
            userService.sendEmailCode(value, code);
            session.setAttribute("authCode", code);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);

        return ResponseEntity.ok(response);
    }


    @ResponseBody
    @PostMapping("/checkUser")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestBody String requestBody, HttpSession session) throws IOException {
        // JSON 파싱
        Gson gson = new Gson();
        Properties prop = gson.fromJson(requestBody, Properties.class);
        String code = prop.getProperty("code");
        log.debug("code : " + code);

        // 세션에서 인증 코드 가져오기
        String authCode = (String) session.getAttribute("authCode");
        log.debug("authCode : " + authCode);

        // 결과 생성
        Map<String, Object> jsonResponse = new HashMap<>();

        if (authCode != null && authCode.equals(code)) {
            jsonResponse.put("result", 1);
        } else {
            jsonResponse.put("result", 0);
        }
        return ResponseEntity.ok(jsonResponse);
    }
}
