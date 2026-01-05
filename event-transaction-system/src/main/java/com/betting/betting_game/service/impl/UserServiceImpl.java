package com.betting.betting_game.service.impl;

import com.betting.betting_game.model.entity.User;
import com.betting.betting_game.model.entity.UserLogin;
import com.betting.betting_game.repository.UserRepository;
import com.betting.betting_game.service.service.JwtTokenService;
import com.betting.betting_game.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenService jwtTokenService;


    @Override
    public User createUser(User user) {
        registerUser(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        //kayıt varmı kontrol edilir
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public void registerUser(User user) {

        if (userRepository.existsByMail(user.getMail())) {
            throw new RuntimeException("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); //şifre şifrelenir
        userRepository.save(user);


    }

    @Override
    public String authenticateUser(UserLogin userLogin) {
        User existingUser = userRepository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(userLogin.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtTokenService.generateToken(existingUser.getMail()); // JWT token üretimi

    }

}
