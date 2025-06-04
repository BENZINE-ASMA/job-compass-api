package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Auth.AuthResponse;
import com.dauphine.jobCompass.dto.Auth.LoginRequest;
import com.dauphine.jobCompass.services.Auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication", description = "API pour l'authentification des utilisateurs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifier un utilisateur avec son email et mot de passe"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie, token JWT retourné"),
            @ApiResponse(responseCode = "400", description = "Données de connexion invalides (email ou mot de passe manquant)"),
            @ApiResponse(responseCode = "401", description = "Identifiants incorrects"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}