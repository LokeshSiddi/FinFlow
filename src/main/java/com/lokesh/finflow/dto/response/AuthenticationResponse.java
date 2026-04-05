package com.lokesh.finflow.dto.response;

public record AuthenticationResponse(
        String token,
        String email,
        String role
) {}