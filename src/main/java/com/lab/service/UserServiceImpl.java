package com.lab.service;

import com.lab.exception.UserNotFoundException;
import com.lab.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Map<Long, User> USERS = Map.of(
            1L, new User(1L, "Alice Smith", "alice@example.com"),
            2L, new User(2L, "Bob Johnson", "bob@example.com"),
            3L, new User(3L, "Carol White", "carol@example.com"));

    @Override
    public User findById(Long id) {
        User user = USERS.get(id);
        if (user == null)
            throw new UserNotFoundException(id);
        return user;
    }
}