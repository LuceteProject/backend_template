package com.lucete.template.info.controller;

import io.jsonwebtoken.io.IOException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    public TokenController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/token")
    public String getToken(OAuth2AuthenticationToken authentication,HttpServletResponse response) throws IOException, java.io.IOException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            // 에러 처리
        }
        String accessToken = client.getAccessToken().getTokenValue();

        // 로그로 엑세스 토큰 출력
        System.out.println("Access Token: " + accessToken);

        return accessToken;
    }
}
