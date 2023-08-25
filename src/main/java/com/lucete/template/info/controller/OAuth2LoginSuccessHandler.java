package com.lucete.template.info.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getName());

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        DefaultOAuth2User user = (DefaultOAuth2User) token.getPrincipal();

        Map<String, Object> responseAttributes = (Map<String, Object>) user.getAttributes().get("response");

        if (responseAttributes != null) {
            String email = (String) responseAttributes.get("email");
            String name = (String) responseAttributes.get("name");

            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("token", accessToken);
            session.setAttribute("email", email);
            session.setAttribute("name", name);
            System.out.println("email: " + email);
        } else {
            // response 객체가 없는 경우의 처리
        }
        */
    }

}
