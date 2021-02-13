package com.alan.lingua.controller;

import com.alan.lingua.dto.request.AddTranslationDto;
import com.alan.lingua.dto.request.CreateCategoryDto;
import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.dto.response.CategoryTranslationDto;
import com.alan.lingua.service.CategoryService;
import com.alan.lingua.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/person/{personId}/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final TranslationService translationService;

    @Autowired
    public CategoryController(CategoryService categoryService, TranslationService translationService) {
        this.categoryService = categoryService;
        this.translationService = translationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CategoryDto>> createCategory(Principal principal, @RequestBody CreateCategoryDto categoryDto) {
        return categoryService.createCategory(principal, categoryDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<CategoryDto>>> getCategories(Principal principal) {
        return categoryService.getCategories(principal)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CategoryDto>> getCategory(Principal principal, @PathVariable("categoryId") long id) {
        return categoryService.getCategory(principal, id)
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/{categoryId}/translation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CategoryTranslationDto>> addTranslation(Principal principal, @PathVariable("categoryId") long categoryId,
                                                                       @RequestBody AddTranslationDto addTranslationDto) {
        return translationService.addTranslationToCategory(principal, categoryId, addTranslationDto)
                .map(ResponseEntity::ok);
    }
}
