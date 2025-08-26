package com.ciklum.insurance.service;

import com.ciklum.insurance.model.User;
import com.ciklum.insurance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found with mobile"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String id, User user) {
        User existing = getUserById(id); // throws exception if not found
        if (user.getFirstName() != null) existing.setFirstName(user.getFirstName());
        if (user.getMiddleName() != null) existing.setMiddleName(user.getMiddleName());
        if (user.getLastName() != null) existing.setLastName(user.getLastName());
        if (user.getDob() != null) existing.setDob(user.getDob());
        if (user.getMobile() != null) existing.setMobile(user.getMobile());
        if (user.getEmail() != null) existing.setEmail(user.getEmail());
        if (user.getPassword() != null) existing.setPassword(user.getPassword());
        if (user.getAddress() != null) existing.setAddress(user.getAddress());
        if (user.getAadhar() != null) existing.setAadhar(user.getAadhar());
        if (user.getPan() != null) existing.setPan(user.getPan());

        return userRepository.save(existing);
    }


    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

