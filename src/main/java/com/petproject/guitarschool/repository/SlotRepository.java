package com.petproject.guitarschool.repository;

import com.petproject.guitarschool.models.SlotEntity;
import com.petproject.guitarschool.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<SlotEntity, Long> {

    List<SlotEntity> findAllByIsActiveIsTrue();

    List<SlotEntity> findAllByIsActiveIsTrueAndCreatedBy(UserEntity user);
}
