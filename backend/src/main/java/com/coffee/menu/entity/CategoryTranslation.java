package com.coffee.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_translation", uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "language_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(nullable = false)
    private String name;
}