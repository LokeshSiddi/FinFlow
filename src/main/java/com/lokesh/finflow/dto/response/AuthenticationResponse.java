package com.lokesh.finflow.dto.response;

public record AuthenticationResponse(
        String token,
        String name,
        String email,
        String role
) {}