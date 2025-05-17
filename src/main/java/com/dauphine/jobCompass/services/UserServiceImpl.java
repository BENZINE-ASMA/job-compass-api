package com.dauphine.jobCompass.services;

import com.dauphine.jobCompass.dto.UserCreationRequest;
import com.dauphine.jobCompass.dto.UserDTO;
import com.dauphine.jobCompass.dto.UserUpdateRequest;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;
import com.dauphine.jobCompass.repositories.UserRepository;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
  //  private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }
    @Override
    public User create(UserCreationRequest request) {
        return null;
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getByUserType(UserType userType) {
        return List.of();
    }

    @Override
    public User update(Long id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public User updatePassword(Long id, String newPassword) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
