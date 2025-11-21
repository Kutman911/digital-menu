package com.coffee.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item_translation", uniqueConstraints = @UniqueConstraint(columnNames = {"menu_item_id", "language_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItemTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}