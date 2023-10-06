package com.lucete.template.info.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY;
    public JwtUtil() {
        try {
            // ClassPathResource를 사용하여 secret.key 파일을 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("secret.key");
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            this.SECRET_KEY = new String(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read secret key from file");
        }
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return claims.getBody().getSubject(); // 토큰에서 이메일(Subject) 추출
        } catch (Exception e) {
            return null;
        }
    }
}
