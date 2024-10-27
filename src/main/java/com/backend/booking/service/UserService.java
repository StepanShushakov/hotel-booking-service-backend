package com.backend.booking.service;

import com.backend.booking.exception.UsernameOrEmailAlreadyExistsException;
import com.backend.booking.mapper.UserMapper;
import com.backend.booking.model.dto.request.UserCreateOrUpdateDto;
import com.backend.booking.model.dto.response.UserDetailsDto;
import com.backend.booking.model.dto.response.UserDto;
import com.backend.booking.model.entity.User;
import com.backend.booking.model.enums.UserRole;
import com.backend.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public UserDetailsDto createUser(UserCreateOrUpdateDto userDto, UserRole role) {
        if (userRepository.usernameOrEmailAlreadyExists(userDto.getUsername(), userDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("username or email is already used at portal");
        }
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user = userRepository.save(user);
        return userMapper.toUserDetailsDto(user);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUserDto(user);
    }

    public User findByUserName(String username) {
        return userRepository.findAllByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found!"));
    }
}
