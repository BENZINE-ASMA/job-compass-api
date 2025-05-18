package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.SimpleUserDTO;
import com.dauphine.jobCompass.dto.UserCreationRequest;
import com.dauphine.jobCompass.dto.UserDTO;
import com.dauphine.jobCompass.dto.UserUpdateRequest;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<SimpleUserDTO> getAllSimpleUsers() {
        return this.userService.getAllSimpleUsers();
    }
    @GetMapping("/users/details")
    public List<UserDTO> getAllUsers() {

        return this.userService.getAll();
    }
    @GetMapping("/users/{email}")
    public SimpleUserDTO getSimpleUserByEmail(@PathVariable String email) {
        return this.userService.getSimpleUserByEmail(email);
    }
    @PostMapping("auth/register")
    public SimpleUserDTO createUser(@RequestBody UserCreationRequest userCreationRequest) {
       return  this.userService.create(userCreationRequest);

    }
/*
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) { ... }

    @PostMapping
    public User createUser(@RequestBody UserCreationRequest request) { ... }
    // PATCH /api/v1/users/{id} (Mise à jour partielle)
    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) { ...}
*/


    }