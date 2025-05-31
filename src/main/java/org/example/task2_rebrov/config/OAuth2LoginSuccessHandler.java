package org.example.task2_rebrov.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.entity.Role;
import org.example.task2_rebrov.entity.User;
import org.example.task2_rebrov.repository.UserRepository;
import org.example.task2_rebrov.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Создаем или находим пользователя
        User user = userRepository.findByUsername(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setPassword(""); // Пароль не нужен для OAuth2 пользователей
                    newUser.setRoles(List.of(Role.USER));
                    return userRepository.save(newUser);
                });

        //JWT токен
        String token = jwtUtil.generateToken(user.getUsername());

        // HTML страница, которая сохранит токен и перенаправит пользователя
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Login Success</title>
            </head>
            <body>
                <h2>Login successful!</h2>
                <p>Redirecting...</p>
                <script>
                    localStorage.setItem('token', '%s');
                    setTimeout(function() {
                        window.location.href = '/dashboard'; // или на главную страницу
                    }, 2000);
                </script>
            </body>
            </html>
            """.formatted(token);

        response.setContentType("text/html");
        response.getWriter().write(html);
    }
}