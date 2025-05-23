package com.dauphine.jobCompass.services.user;

import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.dto.User.UserUpdateRequest;
import com.dauphine.jobCompass.exceptions.UserEmailAlreadyExistsException;
import com.dauphine.jobCompass.exceptions.UsernameNotFoundException;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;
import com.dauphine.jobCompass.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=new BCryptPasswordEncoder();
    }
    @Override
    public SimpleUserDTO create(UserCreationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserEmailAlreadyExistsException(request.getEmail());
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType());
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user); userRepository.save(user);
        return userMapper.toSimpleDto(savedUser);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<SimpleUserDTO> getAllSimpleUsers() {
       List<User> users = userRepository.findAll();
       return users.stream()
               .map(userMapper::toSimpleDto)
               .collect(Collectors.toList());
    }

    @Override
    public SimpleUserDTO getSimpleUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toSimpleDto)
                .orElseThrow(()-> new UsernameNotFoundException(email));
    }

    @Override
    public User getById(UUID id) {
       return  userRepository.getById(id);
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
