package com.dauphine.jobCompass.services.user;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.dto.User.UserUpdateRequest;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    SimpleUserDTO create(UserCreationRequest request);

    List<UserDTO> getAll();
    List<SimpleUserDTO> getAllSimpleUsers();
    SimpleUserDTO getSimpleUserByEmail(String email);
    User getById(UUID id);
    List<ApplicationDTO> getApplicationsByUserId(UUID userId);
    List<User> getByUserType(UserType userType);

    User update(Long id, UserUpdateRequest request);
    User updatePassword(Long id, String newPassword);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    /*
    void validateUserTypeTransition(User currentUser, UserType newType);
     */

}
