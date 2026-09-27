package com.foodies.fooddelivery.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodies.fooddelivery.dto.UpdateUserRequest;
import com.foodies.fooddelivery.entity.User;
import com.foodies.fooddelivery.exception.DuplicateEmailException;
import com.foodies.fooddelivery.repository.UserRepository;
import com.foodies.fooddelivery.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {

            throw new DuplicateEmailException(
                    "Email already registered"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        user.setRole("CUSTOMER");

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {

        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User updateUser(
            Long id,
            UpdateUserRequest userRequest,
            String loggedInEmail) {

        User existingUser =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        User loggedInUser =
                userRepository
                        .findByEmailIgnoreCase(loggedInEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Logged-in user not found"
                                )
                        );

        if (!"ADMIN".equalsIgnoreCase(
                loggedInUser.getRole())
                && !existingUser.getEmail()
                        .equalsIgnoreCase(loggedInEmail)) {

            throw new RuntimeException(
                    "You are not allowed to update this profile"
            );
        }

        existingUser.setName(
                userRequest.getName()
        );

        existingUser.setEmail(
                userRequest.getEmail()
        );

        existingUser.setPhone(
                userRequest.getPhone()
        );

        existingUser.setAddress(
                userRequest.getAddress()
        );

        if ("ADMIN".equalsIgnoreCase(
                loggedInUser.getRole())) {

            existingUser.setRole(
                    userRequest.getRole()
            );
        }

        if (userRequest.getPassword() != null
                && !userRequest.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(
                            userRequest.getPassword()
                    )
            );
        }

        return userRepository.save(
                existingUser
        );
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }
}