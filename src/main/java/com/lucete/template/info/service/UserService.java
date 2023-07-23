package com.lucete.template.info.service;

import com.lucete.template.info.DTO.TodoDTO;
import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.DTO.UserInfo;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Todo;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

    }


    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        modelMapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
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
        userDTO.setTeam_code(user.getTeamCode());
        userDTO.setProfile_message(user.getProfileMessage());
        return userDTO;
    }
    public UserDTO registerUser(UserInfo userinfoDTO) {
        User user = modelMapper.map(userinfoDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the password
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email));
        if (!passwordEncoder.matches(password, user.getPassword())) {  // Check if the password matches
            throw new BadCredentialsException("Invalid password");
        }
        return modelMapper.map(user, UserDTO.class);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
