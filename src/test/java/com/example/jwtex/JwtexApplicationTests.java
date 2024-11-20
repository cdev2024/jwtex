package com.example.jwtex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
class JwtexApplicationTests {
    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
    }

    @Test
    void tokenCreate(){
        var claims = new HashMap<String, Object>();
        claims.put("user_id", 7777);

        var expiredAt = LocalDateTime.now().plusSeconds(60);

        var jwtToken = jwtService.create(claims, expiredAt);

        System.out.println(jwtToken);
    }

    @Test
    void tokenValidation(){
        var token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo3Nzc3LCJleHAiOjE3MzIwNjMwMjN9.r_xlFsx-tPId-cCYLBdsAflGzHffgMT1DrWl95aocHI";
        jwtService.validation(token);
    }

}
