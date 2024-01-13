package com.petproject.guitarschool.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotDto {
    private Long id;
    @NotNull(message = "Поле не заполнено")
    private LocalTime startTime;
    @NotNull(message = "Поле не заполнено")
    private LocalTime endTime;
    @NotNull(message = "Поле не заполнено")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Указываем формат даты
    private LocalDate date;
    private Long createdById;
    private LocalDateTime createdAt;
}
