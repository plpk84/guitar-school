package com.petproject.guitarschool.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "slots")
public class SlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private LocalDate date;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;
}
