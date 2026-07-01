package com.tinasheGomo.EventManagementSystem.config;

import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.EventManagementSystem.enums.UserRole;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.tinasheGomo.EventManagementSystem.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("tinashegomo96@gmail.com").isEmpty()) {
            OrganizationEntity org = new OrganizationEntity();
            org.setName("EventFlow Demo");
            org.setOwnerId("seeded");
            org = organizationRepository.save(org);

            UserEntity admin = new UserEntity();
            admin.setUid(java.util.UUID.randomUUID().toString());
            admin.setEmail("tinashegomo96@gmail.com");
            admin.setDisplayName("Tinashe Gomo");
            admin.setPassword(passwordEncoder.encode("Tinashe@123"));
            admin.setRole(UserRole.ADMIN);
            admin.setPhone("0774964677");
            admin.setOrganization(org);
            admin = userRepository.save(admin);

            org.setOwnerId(admin.getId());
            organizationRepository.save(org);

            System.out.println("Default admin created: tinashegomo96@gmail.com / Tinashe@123");
        }
    }
}
