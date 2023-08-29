package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;
import com.lucete.template.info.repository.mapping.UserInfoMapping;
import com.lucete.template.info.repository.mapping.UserProfileMapping;
import com.lucete.template.info.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
// 이 컨트롤러의 모든 엔드포인트의 기본 URL 경로는 "/user" 입니다.
@RequestMapping("api/v1/users")
@Tag(name = "users", description = "사용자 API")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 사용자 정보 조회", description = "사용자 ID를 이용하여 사용자 정보를 조회합니다.")

    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "새로운 사용자 생성", description = "새로운 사용자 정보를 입력하여 생성합니다.")

    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "사용자 정보 수정", description = "사용자 ID를 이용하여 기존 사용자 정보를 수정합니다.")

    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "사용자 ID를 이용하여 사용자 정보를 삭제합니다.")

    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    @Operation(summary = "모든 사용자 정보 조회")
    public ResponseEntity<List<UserInfoMapping>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "특정 사용자 기수, 팀, 이름 조회", description = "사용자 ID를 이용하여 사용자 프로필 정보를 조회합니다.")

    public ResponseEntity<UserProfileMapping> getUserProfile(@PathVariable Long userId) {
        UserProfileMapping user = userService.getUserProfile(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser(OAuth2AuthenticationToken token) {
        OAuth2User googleUser = token.getPrincipal();
        return userService.createGoogleUser(googleUser);
    }
    @GetMapping("/forgot-password")
    public RedirectView forgotPassword() {
        return new RedirectView("https://accounts.google.com/signin/recovery");
    }
}

