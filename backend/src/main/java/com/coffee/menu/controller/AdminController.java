package com.coffee.menu.controller;

import com.coffee.menu.entity.*;
import com.coffee.menu.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryRepository categoryRepository;
    private final CategoryTranslationRepository translationRepository;
    private final LanguageRepository languageRepository;

    private final Path uploadPath = Paths.get("../uploads/images").toAbsolutePath().normalize();

    // Создать категорию
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(
            @RequestParam String nameRu,
            @RequestParam String nameEn,
            @RequestParam(required = false) MultipartFile image) throws IOException {

        Category category = categoryRepository.save(Category.builder()
                .sortOrder(categoryRepository.count() > 0 ? categoryRepository.findAll().size() + 1 : 1)
                .build());

        Language ru = languageRepository.findByCode("ru").orElseThrow();
        Language en = languageRepository.findByCode("en").orElseThrow();

        translationRepository.save(CategoryTranslation.builder()
                .category(category).language(ru).name(nameRu).build());
        translationRepository.save(CategoryTranslation.builder()
                .category(category).language(en).name(nameEn).build());

        if (image != null && !image.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Files.copy(image.getInputStream(), uploadPath.resolve(filename));
            category.setImageUrl("/images/" + filename);
            categoryRepository.save(category);
        }

        return ResponseEntity.ok().body("Категория создана: " + nameRu);
    }
}