package com.coffee.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "language")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 5, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;
}