package com.kachina.identity_service.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.kachina.identity_service.dto.request.CandidateCreationRequest;
import com.kachina.identity_service.dto.request.EmployerCreationRequest;
import com.kachina.identity_service.dto.response.*;
import com.kachina.identity_service.entity.*;
import com.kachina.identity_service.enums.ERole;
import com.kachina.identity_service.enums.Gender;
import com.kachina.identity_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User toUser(EmployerCreationRequest request) {
        Role roleEmployer = roleRepository.findByName(ERole.EMPLOYER).get();
        Set<Role> roles = new HashSet<>();
        roles.add(roleEmployer);
        return User.builder()
                .full_name(CharacterReference.encode(request.getFull_name()))
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .description("")
                .phone_number(request.getPhone_number())
                .address(CharacterReference.encode(request.getAddress()))
                .avatar_url("")
                .roles(roles)
                .company_name(CharacterReference.encode(request.getCompany_name()))
                .enabled(true)
                .gender(request.getGender())
                .date_of_birth(new Date())
                .created_at(new Date())
                .updated_at(new Date())
                .build();
    }

    public User toUser(CandidateCreationRequest request) {
        Role roleCanidate = roleRepository.findByName(ERole.CANDIDATE).get();
        Set<Role> roles = new HashSet<>();
        roles.add(roleCanidate);
        return User.builder()
                .full_name(CharacterReference.encode(request.getFull_name()))
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .description("")
                .phone_number("")
                .address("")
                .avatar_url("")
                .roles(roles)
                .company_name("")
                .enabled(true)
                .gender(Gender.MALE)
                .date_of_birth(new Date())
                .created_at(new Date())
                .updated_at(new Date())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getUsername())
            .full_name(CharacterReference.decode(user.getFull_name()))
            .description(CharacterReference.decode(user.getDescription()))
            .address(CharacterReference.decode(user.getAddress()))
            .date_of_birth(user.getDate_of_birth())
            .created_at(user.getCreated_at())
            .updated_at(user.getUpdated_at())
            .company_name(CharacterReference.decode(user.getCompany_name()))
            .phone_number(user.getPhone_number())
            .enabled(user.isEnabled())
            .avatar_url(user.getAvatar_url())
            .gender(user.getGender())
            .roles(user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList()))
            .build();
    }

}
