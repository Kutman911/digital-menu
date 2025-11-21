package com.coffee.menu.config;

import com.coffee.menu.entity.Language;
import com.coffee.menu.repository.LanguageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final LanguageRepository languageRepository;

    public DataInitializer(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @PostConstruct
    public void init() {
        if (languageRepository.count() == 0) {
            languageRepository.save(Language.builder().code("ru").name("Русский").build());
            languageRepository.save(Language.builder().code("en").name("English").build());
            System.out.println("Языки ru и en добавлены в базу!");
        }
    }
}