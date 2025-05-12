package com.kachina.identity_service.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kachina.identity_service.entity.Role;
import com.kachina.identity_service.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByName(ERole name);

}
