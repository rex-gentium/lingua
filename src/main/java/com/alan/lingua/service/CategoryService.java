package com.alan.lingua.service;

import com.alan.lingua.dto.request.CreateCategoryDto;
import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.mapper.CategoryMapper;
import com.alan.lingua.repository.CategoryRepository;
import com.alan.lingua.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class CategoryService extends PrincipalService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(PersonRepository personRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        super(personRepository);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /*public Mono<CategoryDto> createCategory(Principal principal, CreateCategoryDto createCategoryDto) {
        String name = createCategoryDto.getName();
        return getPerson(principal)
                .map(p -> categoryRepository.findFirstByPersonIdAndName(p.getId(), name))
    }*/
}
