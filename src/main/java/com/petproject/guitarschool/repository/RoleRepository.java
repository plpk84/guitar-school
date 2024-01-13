package com.petproject.guitarschool.repository;

import com.petproject.guitarschool.models.RoleEntity;
import com.petproject.guitarschool.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Role name);
}
