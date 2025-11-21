package com.coffee.menu.repository;

import com.coffee.menu.entity.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, Long> {
    List<CategoryTranslation> findByCategoryId(Long categoryId);
}