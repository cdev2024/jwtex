package com.example.jwtex;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private static final String secretKey = "SpringSecurityKey_P@ssword_http://Spring.io";

    // 토큰 생성
    public String create(
            Map<String, Object> claims,
            LocalDateTime expireAt
    ){
        var secretKeyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        var key = Keys.hmacShaKeyFor(secretKeyBytes);

        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .claims(claims)
                .expiration(_expireAt)
                .compact();
    }

    // 토큰 검증
    public void validation(String token){
        var secretKeyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        var key = Keys.hmacShaKeyFor(secretKeyBytes);

        var parser = Jwts.parser()
                .verifyWith(key)
                .build();

        try {
            var result = parser.parseSignedClaims(token);
            result.getPayload().forEach((key1, value1) -> log.info("key: {}, value : {}", key1, value1));
        } catch (Exception e){
            if(e instanceof SignatureException){
                throw new RuntimeException("JWT Token Not Valid Exception");
            } else if (e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Expired Exception");
            } else {
                throw new RuntimeException("JWt Exception");
            }
        }
    }
}
