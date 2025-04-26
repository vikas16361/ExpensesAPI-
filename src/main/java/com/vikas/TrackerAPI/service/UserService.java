package com.vikas.TrackerAPI.service;

import com.vikas.TrackerAPI.entity.User;
import com.vikas.TrackerAPI.model.UserModel;

public interface UserService {
    User createUser(UserModel umodel);
    User readUser(Long id);
    User updateUser(Long id, User umodel);
    void deleteUser(Long id);
    User getLoggedin();
}
