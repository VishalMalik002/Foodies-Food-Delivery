package com.foodies.fooddelivery.service;

import com.foodies.fooddelivery.dto.LoginRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);
}