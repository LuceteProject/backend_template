package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UsersRepository;
import com.lucete.template.info.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @Operation(summary = "특정 사용자 정보 조회", description = "사용자 ID를 이용하여 사용자 정보를 조회합니다.")

    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "새로운 사용자 생성", description = "새로운 사용자 정보를 입력하여 생성합니다.")

    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "사용자 정보 수정", description = "사용자 ID를 이용하여 기존 사용자 정보를 수정합니다.")

    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자 ID를 이용하여 사용자 정보를 삭제합니다.")

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    @Operation(summary = "모든 사용자 정보 조회", description = "페이징 기능을 이용하여 모든 사용자 정보를 조회합니다.")

    public Page<UserDTO> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }
}

