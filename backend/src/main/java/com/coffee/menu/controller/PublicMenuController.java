package com.coffee.menu.controller;

import com.coffee.menu.entity.*;
import com.coffee.menu.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class PublicMenuController {

    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryTranslationRepository categoryTranslationRepository;
    private final MenuItemRepository menuItemRepository;

    // 1. Список языков
    @GetMapping("/languages")
    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    // 2. Категории на выбранном языке
    @GetMapping("/{lang}/categories")
    public List<CategoryDto> getCategories(@PathVariable String lang) {
        Language language = languageRepository.findByCode(lang)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        return categoryRepository.findAllByActiveTrueOrderBySortOrder().stream()
                .map(cat -> {
                    String name = cat.getTranslations().stream()
                            .filter(t -> t.getLanguage().equals(language))
                            .findFirst()
                            .map(CategoryTranslation::getName)
                            .orElse(cat.getId().toString());

                    return new CategoryDto(cat.getId(), name, cat.getImageUrl());
                })
                .toList();
    }

    // 3. Позиции в категории
    @GetMapping("/{lang}/category/{catId}")
    public List<MenuItemDto> getItems(@PathVariable String lang, @PathVariable Long catId) {
        Language language = languageRepository.findByCode(lang)
                .orElseThrow();

        return menuItemRepository.findByCategoryIdAndActiveTrueOrderBySortOrder(catId).stream()
                .map(item -> {
                    String name = item.getTranslations().stream()
                            .filter(t -> t.getLanguage().equals(language))
                            .findFirst()
                            .map(MenuItemTranslation::getName)
                            .orElse("No name");

                    String desc = item.getTranslations().stream()
                            .filter(t -> t.getLanguage().equals(language))
                            .findFirst()
                            .map(MenuItemTranslation::getDescription)
                            .orElse("");

                    return new MenuItemDto(item.getId(), name, desc, item.getPrice(), item.getImageUrl());
                })
                .toList();
    }
}

// DTO-классы (вставь в этот же файл или отдельно)
record CategoryDto(Long id, String name, String imageUrl) {}
record MenuItemDto(Long id, String name, String description, BigDecimal price, String imageUrl) {}