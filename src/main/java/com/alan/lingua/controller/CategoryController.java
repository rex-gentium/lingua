package com.alan.lingua.controller;

import com.alan.lingua.dto.request.CreateCategoryDto;
import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Controller
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CategoryDto>> createWord(Principal principal, @RequestBody CreateCategoryDto categoryDto) {
        return categoryService.createCategory(principal, categoryDto)
                .map(ResponseEntity::ok);
    }
}
