package com.petproject.guitarschool.repository;

import com.petproject.guitarschool.models.ApplicationEntity;
import com.petproject.guitarschool.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> findAllByIsActiveIsTrue();

    List<ApplicationEntity> findAllByIsActiveIsTrueAndUser(UserEntity user);

    @Query("""
            from ApplicationEntity a
            join fetch a.slot s
            join fetch s.createdBy teacher
            where a.isActive = true and teacher=:user
            """)
    List<ApplicationEntity> findByTeacher(UserEntity user);
}
