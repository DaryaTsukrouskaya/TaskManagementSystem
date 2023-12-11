package ru.effectivemobile.taskmanagementsystem.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;

import java.io.IOException;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION = "Authorization";
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private PasswordEncoder encoder;

    @Autowired
    @Lazy
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public JwtFilter(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            String email = jwtProvider.getAccessClaims(token).getSubject();
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                org.springframework.security.core.userdetails.User principal =
                        new org.springframework.security.core.userdetails.User(
                                user.getEmail(), encoder.encode(user.getPassword()),
                                Stream.of("USER").map(SimpleGrantedAuthority::new).toList());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal,
                        null, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
