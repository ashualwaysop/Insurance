package com.ciklum.insurance.service;

import com.ciklum.insurance.model.User;

import java.util.List;
public interface UserService {
    User registerUser(User user);
    User loginUser(String email, String password);
    User getUserById(String id);
    User getUserByMobile(String mobile);
    List<User> getAllUsers();
    User updateUser(String id, User user);
    void deleteUser(String id);
}
