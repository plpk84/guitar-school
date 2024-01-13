package com.petproject.guitarschool.service;

import com.petproject.guitarschool.dto.SlotDto;
import com.petproject.guitarschool.models.SlotEntity;
import com.petproject.guitarschool.models.UserEntity;
import com.petproject.guitarschool.repository.SlotRepository;
import com.petproject.guitarschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;
    private final UserRepository userRepository;

    public List<SlotDto> findAllActiveSlots() {
        return slotRepository.findAllByIsActiveIsTrue().stream().map(this::mapToSlotDto).toList();

    }

    public List<SlotDto> findSlotsByTeacher(UserEntity user) {
        return slotRepository.findAllByIsActiveIsTrueAndCreatedBy(user).stream().map(this::mapToSlotDto).toList();
    }


    private SlotEntity mapToSlot(SlotDto slotDTO) {
        return SlotEntity.builder()
                .id(slotDTO.getId())
                .startTime(slotDTO.getStartTime())
                .endTime(slotDTO.getEndTime())
                .date(slotDTO.getDate())
                .createdBy(userRepository.findById(slotDTO.getCreatedById()).orElseThrow())
                .build();
    }

    private SlotDto mapToSlotDto(SlotEntity slotEntity) {
        return SlotDto.builder()
                .id(slotEntity.getId())
                .startTime(slotEntity.getStartTime())
                .endTime(slotEntity.getEndTime())
                .date(slotEntity.getDate())
                .createdById(slotEntity.getCreatedBy().getId())
                .build();
    }


    public SlotEntity saveSlot(SlotDto slotDto) {
        return slotRepository.save(mapToSlot(slotDto));
    }

    public Optional<SlotDto> findById(Long id) {
        return slotRepository.findById(id).map(this::mapToSlotDto);
    }

    //todo-> пофиксить created updated
    public void updateSlot(SlotDto slot) {
        slotRepository.findById(slot.getId()).ifPresent(
                s -> {
                    s.setDate(slot.getDate());
                    s.setStartTime(slot.getStartTime());
                    s.setEndTime(slot.getEndTime());
                    slotRepository.save(s);
                }
        );
    }

    public void deleteById(Long id) {
        slotRepository.findById(id).ifPresent(slot -> {
            slot.setActive(false);
            slotRepository.save(slot);
        });
    }
}
