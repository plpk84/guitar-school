package com.petproject.guitarschool.models;

import com.petproject.guitarschool.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, nullable = false)
    Role name;
}
