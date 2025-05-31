package org.example.task2_rebrov.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.LoginRequest;
import org.example.task2_rebrov.dto.RegisterRequest;
import org.example.task2_rebrov.entity.User;
import org.example.task2_rebrov.service.UserService;
import org.example.task2_rebrov.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) { // Повернули ResponseEntity<String>
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            System.out.println("User " + authentication.getName() + " authenticated via /login endpoint. Session ID: " + session.getId());
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            if (currentAuth != null) {
                System.out.println("AuthController: Authentication object in SecurityContext: " + currentAuth.getName() + ", isAuthenticated: " + currentAuth.isAuthenticated());
            } else {
                System.out.println("AuthController: SecurityContext is EMPTY after setting authentication!");
            }

            String token = jwtUtil.generateToken(authentication.getName()); // Генеруємо токен
            return ResponseEntity.ok(token); // Повертаємо токен
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed for user " + loginRequest.getUsername() + " via /login endpoint: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}