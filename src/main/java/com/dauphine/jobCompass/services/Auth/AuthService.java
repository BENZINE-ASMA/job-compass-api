package com.dauphine.jobCompass.services.Auth;

import com.dauphine.jobCompass.dto.Auth.AuthResponse;
import com.dauphine.jobCompass.exceptions.BadCredentialsException;
import com.dauphine.jobCompass.exceptions.UsernameNotFoundException;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if(!passwordEncoder.matches(password,user.getPasswordHash())){
            throw new BadCredentialsException("Invalid password");
        }
        String jwtToken = jwtService.generateJwtToken(user);
        return new AuthResponse(jwtToken,user.getEmail(),user.getUserType().name());
    }
}
