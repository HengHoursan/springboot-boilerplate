package com.example.springbootboilerplate.app.db.seeds;

import com.example.springbootboilerplate.app.models.Role;
import com.example.springbootboilerplate.app.models.User;
import com.example.springbootboilerplate.app.repository.RoleRepository;
import com.example.springbootboilerplate.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void seed() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElse(null);

            User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@gmail.com")
                .status(true)
                .role(adminRole)
                .build();
            
            userRepository.save(admin);
        }
    }
}
