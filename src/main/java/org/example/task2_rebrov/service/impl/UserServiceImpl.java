package org.example.task2_rebrov.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.RegisterRequest;
import org.example.task2_rebrov.entity.Role;
import org.example.task2_rebrov.entity.User;
import org.example.task2_rebrov.repository.UserRepository;
import org.example.task2_rebrov.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(List.of(Role.USER));
        return userRepository.save(user);
    }
}