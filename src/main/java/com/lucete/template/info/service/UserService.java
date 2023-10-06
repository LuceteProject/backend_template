package com.lucete.template.info.service;

import com.lucete.template.info.DTO.TodoDTO;
import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;
import com.lucete.template.info.repository.mapping.UserInfoMapping;
import com.lucete.template.info.repository.mapping.UserProfileMapping;
import com.lucete.template.info.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private HashMap<String, String> passwordMap = new HashMap<>(); // 비밀번호를 저장할 해시맵

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User does not exist"));

        if (!password.equals(user.getPassword())) { // 실제로는 해시된 비밀번호를 비교해야 함
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(email);
    }
    public boolean validateUserByToken(String token) {
        String email = jwtUtil.validateToken(token);
        if (email == null) {
            return false;
        }
        return userRepository.findByEmail(email).isPresent();
    }
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        return convertToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        passwordMap.put(userDTO.getEmail(), userDTO.getPassword());

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
    public List<UserInfoMapping> getAllUsers() {
        List<User> users = userRepository.findAll(); // User 엔티티 리스트 가져오기
        return users.stream()
                .map(this::convertToUserInfoMapping) // User를 UserInfoMapping으로 변환
                .collect(Collectors.toList());
    }

    public UserProfileMapping getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        return convertToUserProfileMapping(user);
    }

    private UserInfoMapping convertToUserInfoMapping(User user) {
        return new UserInfoMapping() {

            @Override
            public Long getId() { return user.getId(); }
            @Override
            public String getName() {
                return user.getName();
            }

            @Override
            public String getEmail() {
                return user.getEmail();
            }

            @Override
            public String getPhone() {
                return user.getPhone();
            }

            @Override
            public Integer getSemester() {
                return user.getSemester();
            }

            @Override
            public Integer getTeamCode() { return user.getTeamCode(); }

            @Override
            public String getProfileMessage() {
                return user.getProfileMessage();
            }

            @Override
            public String getProfileImage() {
                return user.getProfileImage();
            }

            @Override
            public Integer getPermissionCode() { return user.getPermissionCode();}
        };
    }

    private UserProfileMapping convertToUserProfileMapping(User user){
        return new UserProfileMapping() {

            @Override
            public Long getId() { return user.getId(); }

            @Override
            public String getName() { return user.getName(); }

            @Override
            public Integer getTeamCode() { return user.getTeamCode(); }

            @Override
            public Integer getSemester() { return user.getSemester(); }
        };
    }


    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setGoogleId(user.getGoogleId());
        userDTO.setNaverId(user.getNaverId());
        userDTO.setTeamCode(user.getTeamCode());
        userDTO.setAttManager(user.getAttManager());
        userDTO.setPermissionCode(user.getPermissionCode());
        userDTO.setProfileMessage(user.getProfileMessage());
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
