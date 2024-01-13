package com.petproject.guitarschool.service;

import com.petproject.guitarschool.models.InstrumentEntity;
import com.petproject.guitarschool.models.RoleEntity;
import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.models.enums.InstrumentType;
import com.petproject.guitarschool.models.enums.Role;
import com.petproject.guitarschool.repository.InstrumentRepository;
import com.petproject.guitarschool.repository.RoleRepository;
import com.petproject.guitarschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InstrumentRepository instrumentRepository;
    private final PasswordEncoder passwordEncoder;

    @Scheduled(initialDelay = 1000L,fixedRate = 100000000000L)
    @Transactional
    public void createAdminIfNotExist() {
        if(instrumentRepository.findByName(InstrumentType.ELECTRO).isEmpty()){
            instrumentRepository.save(
                    InstrumentEntity.builder().name(InstrumentType.ELECTRO).build()
            );
        }
        if(instrumentRepository.findByName(InstrumentType.UKULELE).isEmpty()){
            instrumentRepository.save(
                    InstrumentEntity.builder().name(InstrumentType.UKULELE).build()
            );
        }
        if(instrumentRepository.findByName(InstrumentType.ACOUSTIC).isEmpty()){
            instrumentRepository.save(
                    InstrumentEntity.builder().name(InstrumentType.ACOUSTIC).build()
            );
        }
        if(roleRepository.findByName(Role.ROLE_ADMIN).isEmpty()){
            roleRepository.save(
                    RoleEntity.builder().name(Role.ROLE_ADMIN).build()
            );
        }
        if(roleRepository.findByName(Role.ROLE_USER).isEmpty()){
            roleRepository.save(
                    RoleEntity.builder().name(Role.ROLE_USER).build()
            );
        }
        if(roleRepository.findByName(Role.ROLE_TEACHER).isEmpty()){
            roleRepository.save(
                    RoleEntity.builder().name(Role.ROLE_TEACHER).build()
            );
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(
                    UserEntity.builder()
                            .role(roleRepository.findByName(Role.ROLE_ADMIN)
                                    .orElseThrow(
                                            () -> new RuntimeException("Не удалось найти роль ADMIN")
                                    )
                            )
                            .email("admin")
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .age(-1)
                            .firstName("admin")
                            .secondName("admin")
                            .thirdName("admin")
                            .build()
            );
        }
    }

}
