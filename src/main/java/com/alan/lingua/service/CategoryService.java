package com.alan.lingua.service;

import com.alan.lingua.dto.request.CreateCategoryDto;
import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.exception.AlreadyExistsException;
import com.alan.lingua.exception.ForbiddenActionException;
import com.alan.lingua.exception.NotFoundException;
import com.alan.lingua.mapper.CategoryMapper;
import com.alan.lingua.model.Category;
import com.alan.lingua.repository.CategoryRepository;
import com.alan.lingua.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    public Flux<CategoryDto> getCategories(Principal principal) {
        return getPerson(principal)
                .flatMapMany(p -> categoryRepository.findByPersonId(p.getId())
                        .map(categoryMapper::toDto));
    }

    public Mono<CategoryDto> getCategory(Principal principal, long id) {
        return getPerson(principal)
                .flatMap(p -> getCategoryByIdForUser(id, p.getId()))
                .map(categoryMapper::toDto);
    }

    private Mono<Category> saveCategory(String name, Long personId) {
        Category category = new Category();
        category.setName(name);
        category.setPersonId(personId);
        return categoryRepository.save(category);
    }

    private Mono<Category> getCategoryById(long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(
                        "Category with id '{0}' does not exist", id))));
    }

    private Mono<Category> getCategoryByIdForUser(long id, long personId) {
        return getCategoryById(id)
                .filter(c -> c.getPersonId().equals(personId))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ForbiddenActionException(
                        "Category with id '{0}' does not belong to user with id '{1}'", id, personId))));
    }

}
