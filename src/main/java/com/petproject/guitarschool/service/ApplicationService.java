package com.petproject.guitarschool.service;

import com.petproject.guitarschool.dto.ApplicationDto;
import com.petproject.guitarschool.dto.ApplicationInfoDto;
import com.petproject.guitarschool.models.ApplicationEntity;
import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.repository.ApplicationRepository;
import com.petproject.guitarschool.repository.InstrumentRepository;
import com.petproject.guitarschool.repository.SlotRepository;
import com.petproject.guitarschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;
    private final InstrumentRepository instrumentRepository;

    public List<ApplicationInfoDto> findByCreatedBy(UserEntity user) {
        return applicationRepository.findAllByIsActiveIsTrueAndUser(user).stream().map(this::mapToApplicationInfoDto).toList();
    }

    public List<ApplicationInfoDto> findAllActiveApplications() {
        return applicationRepository.findAllByIsActiveIsTrue().stream().map(this::mapToApplicationInfoDto).toList();
    }

    public List<ApplicationInfoDto> findByTeacher(UserEntity user) {
        return applicationRepository.findByTeacher(user).stream().map(this::mapToApplicationInfoDto).toList();
    }

    public Optional<ApplicationDto> findById(Long id) {
        return applicationRepository.findById(id).map(this::mapToApplicationDto);
    }

    public ApplicationEntity saveApplication(ApplicationDto applicationDTO) {
        return applicationRepository.save(mapToApplication(applicationDTO));
    }

    public void updateApplication(ApplicationDto application) {
        applicationRepository.findById(application.getId()).ifPresent(
                applicationEntity -> {
                    applicationEntity.setInstrument(
                            instrumentRepository.findByName(application.getInstrumentType())
                                    .orElseThrow(() -> new RuntimeException("Инструмент" + application.getInstrumentType().name() + "не найден"))
                    );
                    applicationEntity.setSlot(slotRepository.findById(application.getSlotId()).orElseThrow());
                    applicationEntity.setUser(userRepository.findById(application.getUserId()).orElseThrow());
                    applicationRepository.save(applicationEntity);
                }
        );
    }

    public void deleteById(Long id) {
        applicationRepository.findById(id).ifPresent(
                applicationEntity -> {
                    applicationEntity.setIsActive(false);
                    applicationRepository.save(applicationEntity);
                }
        );
    }

    private ApplicationEntity mapToApplication(ApplicationDto applicationDTO) {

        return ApplicationEntity.builder()
                .slot(slotRepository.findById(applicationDTO.getSlotId()).orElseThrow())
                .instrument(instrumentRepository.findByName(applicationDTO.getInstrumentType())
                        .orElseThrow(() -> new RuntimeException("Инструмент" + applicationDTO.getInstrumentType().name() + "не найден")))
                .user(userRepository.findById(applicationDTO.getUserId()).orElseThrow())
                .build();

    }


    private ApplicationDto mapToApplicationDto(ApplicationEntity application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .slotId(application.getSlot().getId())
                .userId(application.getUser().getId())
                .instrumentType(application.getInstrument().getName())
                .build();
    }

    private ApplicationInfoDto mapToApplicationInfoDto(ApplicationEntity application) {
        final var slot = application.getSlot();
        final var user = application.getUser();
        return ApplicationInfoDto.builder()
                .id(application.getId())
                .slotDate(slot.getStartTime() + " - " + slot.getEndTime() + " , " + slot.getDate())
                .userId(user.getId())
                .instrumentType(application.getInstrument().getName().name())
                .build();
    }

}
