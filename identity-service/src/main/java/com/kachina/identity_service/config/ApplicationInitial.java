package com.kachina.identity_service.config;

import java.util.*;
import java.time.LocalDate;

import com.kachina.identity_service.enums.Gender;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kachina.identity_service.repository.RoleRepository;
import com.kachina.identity_service.repository.UserRepository;
import com.kachina.identity_service.entity.Role;
import com.kachina.identity_service.entity.User;
import com.kachina.identity_service.enums.ERole;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitial {
    
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    public ApplicationRunner initialData() {
        return args -> {
            log.info("Start Init Data...");
            Role roleCandidate = getRole(ERole.CANDIDATE);
            Role roleEmployer = getRole(ERole.EMPLOYER);
            Role roleAdmin = getRole(ERole.ADMIN);
            log.info("Initial Admin Account...");
            if(!userRepository.findByUsername(ADMIN_EMAIL).isPresent()) {

                Set<Role> roles = new HashSet<>();
                roles.add(roleCandidate);
                roles.add(roleEmployer);
                roles.add(roleAdmin);

                User admin = User.builder()
                        .username(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .full_name("Admin")
                        .date_of_birth(new Date(2000, 12, 20))
                        .created_at(new Date())
                        .updated_at(new Date())
                        .address(CharacterReference.encode("Hà Nội, Bắc Từ Liêm, Minh Khai"))
                        .enabled(true)
                        .gender(Gender.MALE)
                        .phone_number("0123456789")
                        .description("Admin account.")
                        .roles(roles)
                        .build();

                admin = userRepository.save(admin);
                log.info("\n\u001B[32mAdmin Account Has Been Created!\nusername: " + ADMIN_EMAIL + "\npassword: " + ADMIN_PASSWORD + "\u001B[0m");
            } else {
                log.info("\n\u001B[32mAdmin Account Already Exists!\nusername: " + ADMIN_EMAIL + "\npassword: " + ADMIN_PASSWORD + "\u001B[0m");
            }
        };
    }

    public Role getRole(ERole roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if(!role.isPresent()) return createRole(roleName);
        else return role.get();
    }

    public Role createRole(ERole roleName) {
        log.info("Create Role " + roleName.name());
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

}
