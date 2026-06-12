package com.spring.dreamshop.data;

import com.spring.dreamshop.model.Role;
import com.spring.dreamshop.model.User;
import com.spring.dreamshop.repository.RoleRepository;
import com.spring.dreamshop.repository.UserRepository;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultAdminIfNotExits();
    }

    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow();

        String defaultEmail = "admin@email.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(Set.of(adminRole));
        userRepository.save(user);
        log.info("Default admin user created successfully.");
    }

    private void createDefaultRoleIfNotExits(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
