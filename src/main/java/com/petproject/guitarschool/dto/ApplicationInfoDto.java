package com.petproject.guitarschool.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationInfoDto {
    private Long id;
    private String instrumentType;
    private Long userId;
    private String slotDate;
}
