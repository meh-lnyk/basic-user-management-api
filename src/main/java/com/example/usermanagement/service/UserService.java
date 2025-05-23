package com.example.usermanagement.service;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.security.authentication.BadCredentialsException;

import com.example.usermanagement.config.JwtTokenProvider;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    public String login(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return JwtTokenProvider.generateToken("admin");
        }

        throw new BadCredentialsException("Доступ запрещен");
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUser(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setFathersName(userDetails.getFathersName());
        user.setBirthDate(userDetails.getBirthDate());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        return userRepository.save(user);
    }

    public User patchUser(Long id, Map<String, Object> updates) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (updates.containsKey("firstName"))
        user.setFirstName((String) updates.get("firstName"));

    if (updates.containsKey("lastName"))
        user.setLastName((String) updates.get("lastName"));

    if (updates.containsKey("email"))
        user.setEmail((String) updates.get("email"));

    if (updates.containsKey("phoneNumber"))
        user.setPhoneNumber((String) updates.get("phoneNumber"));

    if (updates.containsKey("birthDate"))
        user.setBirthDate(LocalDate.parse((String) updates.get("birthDate")));

    return userRepository.save(user);
}

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
