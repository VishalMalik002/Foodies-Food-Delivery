package com.foodies.fooddelivery.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.dto.LoginRequest;
import com.foodies.fooddelivery.entity.User;
import com.foodies.fooddelivery.repository.UserRepository;
import com.foodies.fooddelivery.service.AuthService;
import com.foodies.fooddelivery.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String login(LoginRequest loginRequest) {

        User user;

        if (loginRequest.getEmail() != null
                && !loginRequest.getEmail().isBlank()) {

            user = userRepository
                    .findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() ->
                            new RuntimeException("Invalid email/phone or password"));

        } else if (loginRequest.getPhone() != null
                && !loginRequest.getPhone().isBlank()) {

            user = userRepository
                    .findByPhone(loginRequest.getPhone())
                    .orElseThrow(() ->
                            new RuntimeException("Invalid email/phone or password"));

        } else {

            throw new RuntimeException("Email or phone is required");
        }

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email/phone or password");
        }

        return jwtService.generateToken(user);
    }
}