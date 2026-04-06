package com.lokesh.finflow.config;

import com.lokesh.finflow.model.User;
import com.lokesh.finflow.model.UserRole;
import com.lokesh.finflow.model.UserStatus;
import com.lokesh.finflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists to prevent duplicate entries on application restart

        // Admin Seeding:
//        if(!userRepository.existsByEmail("admin@finflow.com")) {
//            User admin = User.builder()
//                    .name("Admin")
//                    .email("admin@finflow.com")
//                    .password(passwordEncoder.encode("admin@123"))
//                    .role(UserRole.ADMIN)
//                    .status(UserStatus.ACTIVE)
//                    .build();
//
//            userRepository.save(admin);
//            System.out.println("✅ Super Admin created : admin@finflow.com / admin@123");
//        }
//
//        // Viewer Seeding:
//        if(!userRepository.existsByEmail("viewer@finflow.com")) {
//            User admin = User.builder()
//                    .name("Viewer")
//                    .email("viewer@finflow.com")
//                    .password(passwordEncoder.encode("viewer@123"))
//                    .role(UserRole.ADMIN)
//                    .status(UserStatus.ACTIVE)
//                    .build();
//
//            userRepository.save(admin);
//            System.out.println("✅ Test Viewer created : viewer@finflow.com / viewer@123");
//        }
    }
}
