package com.dauphine.jobCompass.services.user;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.dto.User.UserUpdateRequest;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.exceptions.UserEmailAlreadyExistsException;
import com.dauphine.jobCompass.mapper.ApplicationMapper;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.repositories.ApplicationRepository;
import com.dauphine.jobCompass.repositories.UserRepository;
import com.dauphine.jobCompass.services.Company.CompanyService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           ApplicationRepository applicationRepository,
                           ApplicationMapper applicationMapper,
                           CompanyService companyService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }
    @Override
    public SimpleUserDTO create(UserCreationRequest request) {

        validateUserCreationRequest(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType());
        user.setCreatedAt(LocalDateTime.now());

        if (request.getCompanyId() != null && !request.getCompanyId().trim().isEmpty()) {
            try {
                Company company = companyService.getCompanyById(UUID.fromString(request.getCompanyId()));
                user.setCompany(company);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Format d'ID d'entreprise invalide: " + request.getCompanyId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Entreprise non trouvée avec l'ID: " + request.getCompanyId());
            }
        }

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toSimpleDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleUserDTO> getAllSimpleUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toSimpleDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleUserDTO getSimpleUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être vide");
        }

        return userRepository.findByEmail(email.toLowerCase().trim())
                .map(userMapper::toSimpleDto)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleUserDTO getSimpleUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        return userRepository.findById(id)
                .map(userMapper::toSimpleDto)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
        }

        List<Application> applications = applicationRepository.findByUserId(userId);
        return applicationMapper.toDtoList(applications);
    }

    @Override
    public User updateUser(UUID id, UserUpdateRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        validateUserUpdateRequest(request);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserEmailAlreadyExistsException(request.getEmail());
            }
            user.setEmail(request.getEmail().toLowerCase().trim());
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }
    @Override
    public User patchUser(UUID id, UserUpdateRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName().trim());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName().trim());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone().trim());
        }

        if (request.getEmail() != null) {
            String newEmail = request.getEmail().toLowerCase().trim();
            if (!newEmail.equals(user.getEmail())) {
                if (userRepository.existsByEmail(newEmail)) {
                    throw new UserEmailAlreadyExistsException(newEmail);
                }
                user.setEmail(newEmail);
            }
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }
/*
    @Override
    public void deleteUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier s'il y a des contraintes empêchant la suppression
        if (applicationRepository.existsByUserId(id)) {
            throw new IllegalStateException("Impossible de supprimer l'utilisateur: il a des candidatures en cours");
        }

        userRepository.delete(user);
    }

    /**
     * Valide les données de création d'utilisateur
     */
    private void validateUserCreationRequest(UserCreationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Les données utilisateur ne peuvent pas être null");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");
        }

        if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }

        if (request.getUserType() == null) {
            throw new IllegalArgumentException("Le type d'utilisateur est obligatoire");
        }


        if (!request.getEmail().contains("@")) {
            throw new IllegalArgumentException("Format d'email invalide");
        }
    }

    private void validateUserUpdateRequest(UserUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Les données de mise à jour ne peuvent pas être null");
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (!request.getEmail().contains("@")) {
                throw new IllegalArgumentException("Format d'email invalide");
            }
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            if (request.getPassword().length() < 6) {
                throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
            }
        }
    }
}