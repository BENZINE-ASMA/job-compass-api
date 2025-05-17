package com.dauphine.jobCompass.services;

import com.dauphine.jobCompass.dto.UserCreationRequest;
import com.dauphine.jobCompass.dto.UserUpdateRequest;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;

import java.util.List;

public interface UserService {
    User create(UserCreationRequest request);

    List<User> getAll();
    User getById(Long id);
    User getByEmail(String email);
    List<User> getByUserType(UserType userType);

    User update(Long id, UserUpdateRequest request);
    User updatePassword(Long id, String newPassword);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    /*
    void validateUserTypeTransition(User currentUser, UserType newType);
     */

}
