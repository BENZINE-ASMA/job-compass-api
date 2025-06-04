package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.dto.User.UserCreationRequest;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.dto.User.UserUpdateRequest;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.UserType;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {

    private final UserService userService;
    private final ApplicationService applicationService;
    private final UserMapper userMapper;

    public UserController(UserService userService, ApplicationService applicationService, UserMapper userMapper) {
        this.userService = userService;
        this.applicationService = applicationService;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    @Operation(
            summary = "Récupérer tous les utilisateurs (informations basiques)",
            description = "Obtenir la liste de tous les utilisateurs avec leurs informations de base"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<SimpleUserDTO>> getAllSimpleUsers() {
        List<SimpleUserDTO> users = userService.getAllSimpleUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/details")
    @Operation(
            summary = "Récupérer tous les utilisateurs (informations détaillées)",
            description = "Obtenir la liste de tous les utilisateurs avec leurs informations complètes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste détaillée des utilisateurs récupérée avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès non autorisé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/email/{email}")
    @Operation(
            summary = "Récupérer un utilisateur par email",
            description = "Obtenir les informations d'un utilisateur spécifique via son adresse email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> getSimpleUserByEmail(
            @Parameter(description = "Adresse email de l'utilisateur", required = true,
                    example = "user@example.com")
            @PathVariable String email) {
        SimpleUserDTO user = userService.getSimpleUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}")
    @Operation(
            summary = "Récupérer un utilisateur par ID",
            description = "Obtenir les informations d'un utilisateur spécifique via son ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> getUserById(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID id) {
        SimpleUserDTO user = userService.getSimpleUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/register")
    @Operation(
            summary = "Enregistrer un nouvel utilisateur",
            description = "Créer un nouveau compte utilisateur dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données utilisateur invalides"),
            @ApiResponse(responseCode = "409", description = "Un utilisateur avec cet email existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> createUser(
            @Parameter(description = "Adresse email", required = true)
            @RequestParam String email,
            @Parameter(description = "Mot de passe", required = true)
            @RequestParam String password,
            @Parameter(description = "Prénom")
            @RequestParam(required = false) String firstName,
            @Parameter(description = "Nom de famille")
            @RequestParam(required = false) String lastName,
            @Parameter(description = "Numéro de téléphone")
            @RequestParam(required = false) String phone,
            @Parameter(description = "Type d'utilisateur", required = true)
            @RequestParam UserType userType,
            @Parameter(description = "ID de l'entreprise (pour les employeurs)")
            @RequestParam(required = false) String company) {

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setEmail(email);
        userCreationRequest.setPassword(password);
        userCreationRequest.setFirstName(firstName);
        userCreationRequest.setLastName(lastName);
        userCreationRequest.setPhone(phone);
        userCreationRequest.setUserType(userType);
        userCreationRequest.setCompanyId(company);

        SimpleUserDTO createdUser = userService.create(userCreationRequest);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @PostMapping("/users")
    @Operation(
            summary = "Créer un nouvel utilisateur (JSON)",
            description = "Créer un nouveau utilisateur en utilisant un body JSON"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données utilisateur invalides"),
            @ApiResponse(responseCode = "409", description = "Un utilisateur avec cet email existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> createUserJson(
            @Valid @RequestBody UserCreationRequest request) {
        SimpleUserDTO createdUser = userService.create(request);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @PutMapping("/users/{id}")
    @Operation(
            summary = "Mettre à jour un utilisateur (complet)",
            description = "Remplacer complètement les informations d'un utilisateur"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Non autorisé à modifier cet utilisateur"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> updateUser(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(id, request);
        SimpleUserDTO userDTO = userMapper.toSimpleDto(updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/users/{id}")
    @Operation(
            summary = "Mettre à jour un utilisateur (partiel)",
            description = "Modifier partiellement les informations d'un utilisateur"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Non autorisé à modifier cet utilisateur"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SimpleUserDTO> patchUser(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        User patchedUser = userService.patchUser(id, request);
        SimpleUserDTO userDTO = userMapper.toSimpleDto(patchedUser);
        return ResponseEntity.ok(userDTO);
    }
/*
    @DeleteMapping("/users/{id}")
    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprimer définitivement un utilisateur du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "403", description = "Non autorisé à supprimer cet utilisateur"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

 */
}