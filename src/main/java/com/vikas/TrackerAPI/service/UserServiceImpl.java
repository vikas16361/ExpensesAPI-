package com.vikas.TrackerAPI.service;

import com.vikas.TrackerAPI.Exception.ItemAlreadyExistException;
import com.vikas.TrackerAPI.Exception.ResourceNotFoundException;
import com.vikas.TrackerAPI.entity.User;
import com.vikas.TrackerAPI.model.UserModel;
import com.vikas.TrackerAPI.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserModel umodel) {
        if(userRepository.existsByEmail(umodel.getEmail())) {
            throw new ItemAlreadyExistException("Email already exists");
        }
        User user = new User();
        BeanUtils.copyProperties(umodel, user);

        return userRepository.save(user);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User updateUser(Long id, User user) {
        User oUser = readUser(id);
        oUser.setName(user.getName()!=null?user.getName(): oUser.getName());
        oUser.setEmail(user.getEmail()!=null?user.getEmail():oUser.getEmail());
        oUser.setPassword(user.getPassword()!=null?user.getPassword():oUser.getPassword());
        oUser.setAge(user.getAge()!=null?user.getAge():oUser.getAge());


        return userRepository.save(oUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getLoggedin() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }


}
