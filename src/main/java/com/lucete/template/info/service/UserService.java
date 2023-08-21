package com.lucete.template.info.service;

import com.lucete.template.info.DTO.TodoDTO;
import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Todo;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        modelMapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToDTO);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setGoogle_id(user.getGoogleId());
        userDTO.setNaver_id(user.getNaverId());
        userDTO.setTeam_code(user.getTeamCode());
        userDTO.setProfile_message(user.getProfileMessage());
        return userDTO;
    }
    public UserDTO createGoogleUser(OAuth2User googleUser) {
        String email = googleUser.getAttribute("email");
        String name = googleUser.getAttribute("name");
        String googleId = googleUser.getAttribute("sub");  // Google ID는 'sub' 속성에 있습니다.

        // 이미 가입한 사용자인지 확인합니다.
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            // 이미 가입한 사용자라면, 그대로 반환합니다.
            return modelMapper.map(existingUser.get(), UserDTO.class);
        }

        // 새 사용자를 생성하고 저장합니다.
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setGoogleId(googleId);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }
}
