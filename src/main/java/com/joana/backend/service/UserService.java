package com.joana.backend.service;

import com.joana.backend.domain.User;
import com.joana.backend.dto.UserRequest;
import com.joana.backend.dto.UserResponse;
import com.joana.backend.exception.UserNotFoundException;
import com.joana.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName()))
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponse(user.getId(), user.getName());
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getName());
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(request.getName());
        User updated = userRepository.save(user);

        return new UserResponse(updated.getId(), updated.getName());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}