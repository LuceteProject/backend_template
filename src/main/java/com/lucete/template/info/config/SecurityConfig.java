package com.lucete.template.info.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login/**").permitAll() // OAuth2 로그인은 인증 없이 접근 가능
                        .requestMatchers("/swagger-ui/**").permitAll() // Swagger UI는 인증 없이 접근 가능
                        .requestMatchers("/api-docs/**").permitAll() // API 문서는 인증 없이 접근 가능
                        .anyRequest().permitAll() // 모든 요청에 대해 인증이 필요
                )
                .oauth2Login(withDefaults()); // OAuth2 로그인을 기본 설정으로 활성화
        return http.build();
    }
}
