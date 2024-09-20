package com.sboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SboardApplication.class, args);
    }

}
