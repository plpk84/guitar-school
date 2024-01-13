package com.petproject.guitarschool.repository;

import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.role r where r.name=:name")
    List<UserEntity> findAllByRole(Role name);
}
