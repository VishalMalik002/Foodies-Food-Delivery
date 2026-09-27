package com.foodies.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import com.foodies.fooddelivery.dto.UpdateUserRequest;
import com.foodies.fooddelivery.entity.User;

public interface UserService {

    User registerUser(User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, UpdateUserRequest userRequest, String loggedInEmail);

    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);
}