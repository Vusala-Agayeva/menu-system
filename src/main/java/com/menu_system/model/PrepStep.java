package com.menu_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prep_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrepStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer stepOrder; // Represents execution sequence (1, 2, 3...)

    @Column(nullable = false)
    private String instruction; // e.g., "Sear beef patty on the grill"

    @Column(nullable = false)
    private Integer estimatedMinutes; // Displayed as a countdown on mobile apps
}
