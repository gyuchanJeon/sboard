package com.sboard.Service;

import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserReporitory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReporitory userReporitory;
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO) {
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);
        User user = userDTO.toEntity();
        userReporitory.save(user);
    }

    public UserDTO selectUser(String uid) {
        Optional<User> opt = userReporitory.findById(uid);
        if (opt.isPresent()) {
            User user = opt.get();
            return user.toDTO();
        }
        return null;
    }

    public List<UserDTO> selectUsers() {
        List<User> users = userReporitory.findAll();
        List<UserDTO> userDTOs = users
                                    .stream()
                                    .map(entity -> entity.toDTO())
                                    .collect(Collectors.toList());
        return userDTOs;
    }

    public void updateUser(UserDTO userDTO) {
        boolean result = userReporitory.existsById(userDTO.getUid());
        if (result) {
            User user = userDTO.toEntity();
            userReporitory.save(user);
        }
    }

    public void deleteUser(String uid) {
        userReporitory.deleteById(uid);
    }

    public boolean checkUid(String type, String value) {
        if (type.equals("uid")) {
            boolean result = userReporitory.existsById(value);
        } else if (type.equals("nick")) {
            boolean result = userReporitory.existsById(value);
        } else if (type.equals("email")) {
            boolean result = userReporitory.existsById(value);
        } else if (type.equals("hp")) {
            boolean result = userReporitory.existsById(value);
        }
        return false;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmailCode(String to, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ian810900@gmail.com"); // 발신자 이메일 주소
        message.setTo(to);
        message.setSubject("sboard 인증 코드입니다.");
        message.setText("인증코드 : " + code);
        mailSender.send(message);
    }

}
