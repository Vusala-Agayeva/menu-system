package com.menu_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private Double price;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private PreparationStatus currentStatus = PreparationStatus.RECEIVED;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "menu_item_id") // Foreign key column in 'prep_steps' table
    @OrderBy("stepOrder ASC")
    private List<PrepStep> preparationSteps = new ArrayList<>();
}
