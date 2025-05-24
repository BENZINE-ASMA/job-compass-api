package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.exceptions.ApiErrorResponse;
import com.dauphine.jobCompass.exceptions.UserEmailAlreadyExistsException;
import com.dauphine.jobCompass.exceptions.UsernameNotFoundException;
import com.dauphine.jobCompass.model.enums.UserType;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "User API",
        description = "Endpoints for managing users"
)

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final ApplicationService applicationService;


    public UserController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Get all users with basic information",
            description = "Retrieves all simple user's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    })
    @GetMapping("/users")
    public ResponseEntity<List<SimpleUserDTO>> getAllSimpleUsers() {

        return ResponseEntity.ok(this.userService.getAllSimpleUsers());
    }

    @Operation(summary = "Get all users with detailed information",
            description = "Retrieves all users with full details")
    @GetMapping("/users/details")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/users/{email}")
    public ResponseEntity<SimpleUserDTO> getSimpleUserByEmail(
            @Parameter(description = "email of user to retrieve", required = true)
            @PathVariable String email) {
        try {
            SimpleUserDTO user = this.userService.getSimpleUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("auth/register")
    public ResponseEntity<?> createUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phone,
            @RequestParam (required = true) UserType userType) {

        try {
            UserCreationRequest userCreationRequest = new UserCreationRequest();
            userCreationRequest.setEmail(email);
            userCreationRequest.setPassword(password);
            userCreationRequest.setFirstName(firstName);
            userCreationRequest.setLastName(lastName);
            userCreationRequest.setPhone(phone);
            userCreationRequest.setUserType(userType);

            SimpleUserDTO createdUser = this.userService.create(userCreationRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserEmailAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("Email already exists: " + email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("Invalid user data: " + e.getMessage()));
        }
    }
    @GetMapping("/{userId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForUser(@PathVariable UUID userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
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