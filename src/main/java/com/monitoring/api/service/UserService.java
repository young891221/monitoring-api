package com.monitoring.api.service;

import com.monitoring.api.domain.User;
import com.monitoring.api.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by young891221@gmail.com on 2018-03-26
 * Blog : http://haviyj.tistory.com
 * Github : http://github.com/young891221
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User findLastCreatedUser() {
        return userRepository.findFirstByOrderByCreatedDateDesc();
    }
}
