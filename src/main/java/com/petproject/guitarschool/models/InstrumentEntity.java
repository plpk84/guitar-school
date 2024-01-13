package com.petproject.guitarschool.models;

import com.petproject.guitarschool.models.enums.InstrumentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "instruments")
public class InstrumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, nullable = false)
    private InstrumentType name;
}
