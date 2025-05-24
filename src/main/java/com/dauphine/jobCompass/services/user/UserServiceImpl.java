package com.dauphine.jobCompass.services.user;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.dto.User.UserUpdateRequest;
import com.dauphine.jobCompass.exceptions.UserEmailAlreadyExistsException;
import com.dauphine.jobCompass.exceptions.UsernameNotFoundException;
import com.dauphine.jobCompass.mapper.ApplicationMapper;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;
import com.dauphine.jobCompass.repositories.ApplicationRepository;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.repositories.UserRepository;
import com.dauphine.jobCompass.services.Company.CompanyService;
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
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper,
                           ApplicationRepository applicationRepository,ApplicationMapper applicationMapper, CompanyService companyService){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=new BCryptPasswordEncoder();
        this.applicationRepository =applicationRepository;
        this.applicationMapper=applicationMapper;
        this.companyService=companyService;
    }
    public SimpleUserDTO create(UserCreationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
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

        if (request.getCompanyId() != null) {
            Company company = companyService.getCompanyById(UUID.fromString(request.getCompanyId()));
            user.setCompany(company);
        }

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

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
    public List<ApplicationDTO> getApplicationsByUserId(UUID userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        return applicationMapper.toDtoList(applications);
    }
    @Override
    public User updateUser(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType());

        return userRepository.save(user);
    }

    @Override
    public User patchUser(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getUserType() != null) user.setUserType(request.getUserType());

        return userRepository.save(user);
    }

}
