package com.alan.lingua.service;

import com.alan.lingua.dto.request.CreateCategoryDto;
import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.exception.AlreadyExistsException;
import com.alan.lingua.mapper.CategoryMapper;
import com.alan.lingua.model.Category;
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

    public Mono<CategoryDto> createCategory(Principal principal, CreateCategoryDto createCategoryDto) {
        String name = createCategoryDto.getName();
        return getPerson(principal)
                .flatMap(p -> categoryRepository.findFirstByPersonIdAndName(p.getId(), name)
                        .flatMap(__ -> Mono.error(new AlreadyExistsException(
                                "Category '{0}' already exists for user '{1}'", name, p.getName())))
                        .switchIfEmpty(Mono.defer(() -> saveCategory(name, p.getId())))
                        .cast(Category.class)
                        .map(categoryMapper::toDto));
    }

    private Mono<Category> saveCategory(String name, Long personId) {
        Category category = new Category();
        category.setName(name);
        category.setPersonId(personId);
        return categoryRepository.save(category);
    }
}
