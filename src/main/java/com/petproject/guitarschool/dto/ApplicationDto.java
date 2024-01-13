package com.petproject.guitarschool.dto;

import com.petproject.guitarschool.models.enums.InstrumentType;
import lombok.*;

@Data
@Builder
public class ApplicationDto {
    private Long id;
    private Long userId;
    private Long slotId;
    private InstrumentType instrumentType;
}
