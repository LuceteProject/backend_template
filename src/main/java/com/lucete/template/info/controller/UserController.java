package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.DTO.UserInfo;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;
import com.lucete.template.info.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Map;
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
    @Operation(summary = "모든 사용자 정보 조회", description = "페이징 기능을 이용하여 모든 사용자 정보를 조회합니다.")
    public Page<UserDTO> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }
    @GetMapping("/signup")
    public String signup(UserInfo userCreateForm) {
        return "signup_form";
    }
    @PostMapping("/signup")
    @Operation(summary = "새로운 사용자 등록", description = "새로운 사용자 정보를 입력하여 등록합니다.")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserInfo userinfoDTO) {
        UserDTO createdUser = userService.registerUser(userinfoDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "이메일과 비밀번호를 이용하여 로그인합니다.")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        UserDTO loggedInUser = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}

