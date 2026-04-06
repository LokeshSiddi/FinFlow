package com.lokesh.finflow.dto.response;

import com.lokesh.finflow.model.UserRole;
import com.lokesh.finflow.model.UserStatus;

public record UserResponse(
    Long id,
    String name,
    String email,
    UserRole role,
    UserStatus status
) {}
