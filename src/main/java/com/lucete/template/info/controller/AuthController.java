package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.model.User;
import com.lucete.template.info.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService; // UserService를 Autowired로 주입

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = payload.get("email");
        String password = payload.get("password");

        Map<String, Object> responseClient = new HashMap<>();
        responseClient.put("success", true);
        responseClient.put("message", "로그인 성공");

        // 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("password", password);

        return ResponseEntity.ok(responseClient);
    }
    @PostMapping("/api/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> payload, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = payload.get("email");
        String password = payload.get("password");
        String name = payload.get("name");
        String phone = payload.get("phone");

        Map<String, Object> responseClient = new HashMap<>();
        responseClient.put("success", true);
        responseClient.put("message", "회원가입 성공");

        // 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        session.setAttribute("name", name);
        session.setAttribute("phone", phone);

        return ResponseEntity.ok(responseClient);
    }
    @PostMapping("/api/get-token-and-user-info")
    public ResponseEntity<Map<String, Object>> exchangeTokenForAccessToken(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        Map<String, Object> response = new HashMap<>();

        if (code == null || code.trim().isEmpty()) {
            response.put("success", "false");
            response.put("message", "Invalid authentication code.");
            return ResponseEntity.badRequest().body(response);
        }

        String tokenRequestUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id="
                + clientId + "&client_secret=" + clientSecret + "&code=" + code;

        ResponseEntity<Map> naverResponse = restTemplate.exchange(tokenRequestUrl, HttpMethod.GET, null, Map.class);
        Map<String, Object> naverResponseData = naverResponse.getBody();

        if (naverResponse.getStatusCode() == HttpStatus.OK) {
            String accessToken = (String) naverResponseData.get("access_token");

            // 네이버 사용자 프로필 API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> userProfileResponse = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, entity, Map.class);

            Map<String, Object> userProfileData = (Map<String, Object>) userProfileResponse.getBody().get("response");
            String email = (String) userProfileData.get("email");

            // 이메일을 이용하여 사용자 정보 조회
            UserDTO user = userService.getUserByEmail(email);

            Map<String, Object> responseClient = new HashMap<>();
            responseClient.put("success", true);
            responseClient.put("token", accessToken);
            responseClient.put("expires", naverResponseData.get("expires_in"));

            responseClient.put("user", user); // 조회한 사용자 정보를 추가
            System.out.println("user: " + user.getName()+user.getEmail()+user.getPhone());
            return ResponseEntity.ok(responseClient);
        } else {
            response.put("success", "false");
            response.put("message", "Failed to retrieve access token.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
