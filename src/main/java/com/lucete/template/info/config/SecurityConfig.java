package com.lucete.template.info.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api*", "/api-docs/**", "/swagger-ui/**").permitAll() // /swagger-ui/** 경로에 대해 인증이 필요하지 않음
                        .anyRequest().authenticated() // 나머지 모든 요청에 대해 인증이 필요
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/login") // 사용자 정의 로그인 페이지 URL
                            .successHandler(new CustomOAuth2LoginSuccessHandler()); // 사용자 정의 로그인 성공 핸들러
                });
        return http.build();
    }
}
