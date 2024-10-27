package com.backend.booking.controller;

import com.backend.booking.model.dto.request.UserCreateOrUpdateDto;
import com.backend.booking.model.dto.response.UserDetailsDto;
import com.backend.booking.model.dto.response.UserDto;
import com.backend.booking.model.enums.UserRole;
import com.backend.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDto> registerUser(@Valid @RequestBody UserCreateOrUpdateDto userDto, @RequestParam(name = "userRole") UserRole role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto, role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
