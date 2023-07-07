package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public UserDTO register(OAuth2AuthenticationToken token) {
        OAuth2User googleUser = token.getPrincipal();
        return userService.createGoogleUser(googleUser);
    }
}
