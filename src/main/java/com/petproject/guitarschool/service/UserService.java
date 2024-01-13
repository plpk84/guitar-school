package com.petproject.guitarschool.service;

import com.petproject.guitarschool.dto.RegistrationDto;
import com.petproject.guitarschool.dto.UserInfoDto;
import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.models.enums.Role;
import com.petproject.guitarschool.repository.RoleRepository;
import com.petproject.guitarschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void saveUser(RegistrationDto registrationDto) {
        var user = UserEntity.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .firstName(registrationDto.getFirstName())
                .secondName(registrationDto.getSecondName())
                .thirdName(registrationDto.getThirdName())
                .age(registrationDto.getAge())
                .role(roleRepository.findByName(Role.ROLE_USER)
                        .orElseThrow(
                                () -> new RuntimeException("Не удалось найти роль USER")
                        )
                )
                .build();
        userRepository.save(user);
    }
    public void saveTeacher(RegistrationDto registrationDto) {
        var user = UserEntity.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .firstName(registrationDto.getFirstName())
                .secondName(registrationDto.getSecondName())
                .thirdName(registrationDto.getThirdName())
                .age(registrationDto.getAge())
                .role(roleRepository.findByName(Role.ROLE_TEACHER)
                        .orElseThrow(
                                () -> new RuntimeException("Не удалось найти роль TEACHER")
                        )
                )
                .build();
        userRepository.save(user);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserInfoDto> findAllActiveTeachers() {
        return userRepository.findAllByRole(Role.ROLE_TEACHER).stream().map(this::toInfoDto).toList();
    }
    public List<UserInfoDto> findAllActiveUsers() {
        return userRepository.findAllByRole(Role.ROLE_USER).stream().map(this::toInfoDto).toList();
    }

    private UserInfoDto toInfoDto(UserEntity user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .thirdName(user.getThirdName())
                .build();
    }
}
