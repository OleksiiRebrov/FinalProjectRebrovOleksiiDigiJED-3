package org.example.task2_rebrov.service;

import org.example.task2_rebrov.dto.RegisterRequest;
import org.example.task2_rebrov.entity.User;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
}