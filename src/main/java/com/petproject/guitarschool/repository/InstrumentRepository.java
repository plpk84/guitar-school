package com.petproject.guitarschool.repository;

import com.petproject.guitarschool.models.InstrumentEntity;
import com.petproject.guitarschool.models.enums.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstrumentRepository extends JpaRepository<InstrumentEntity, Long> {
    Optional<InstrumentEntity> findByName(InstrumentType instrumentType);
}
