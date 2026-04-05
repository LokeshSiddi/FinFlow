package com.lokesh.finflow.service;

import com.lokesh.finflow.dto.request.AuthenticationRequest;
import com.lokesh.finflow.dto.request.RegisterRequest;
import com.lokesh.finflow.dto.response.AuthenticationResponse;
import com.lokesh.finflow.model.User;
import com.lokesh.finflow.model.UserPrincipal;
import com.lokesh.finflow.model.UserStatus;
import com.lokesh.finflow.repository.UserRepository;
import com.lokesh.finflow.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);

        UserPrincipal userPrincipal = new UserPrincipal(user);
        String jwtToken = jwtService.generateToken(userPrincipal, user.getRole().name());

        return new AuthenticationResponse(jwtToken, user.getEmail(), user.getRole().name());
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        // This will throw an exception if credentials are bad or account is locked/disabled
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserPrincipal userPrincipal = new UserPrincipal(user);
        String jwtToken = jwtService.generateToken(userPrincipal, user.getRole().name());

        return new AuthenticationResponse(jwtToken, user.getEmail(), user.getRole().name());
    }
}
