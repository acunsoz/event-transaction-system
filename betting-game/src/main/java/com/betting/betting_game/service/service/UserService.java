package com.betting.betting_game.service.service;

import com.betting.betting_game.model.entity.User;
import com.betting.betting_game.model.entity.UserLogin;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUser();

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    //register - login
    void registerUser(User user);

    String authenticateUser(UserLogin userLogin);


}
