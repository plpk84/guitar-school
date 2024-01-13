package com.petproject.guitarschool.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "applications")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private InstrumentEntity instrument;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SlotEntity slot;
}


