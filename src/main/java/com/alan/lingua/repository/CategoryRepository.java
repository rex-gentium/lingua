package com.alan.lingua.repository;

import com.alan.lingua.model.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends R2dbcRepository<Category, Long> {
    Mono<Category> findFirstByPersonIdAndName(Long personId, String name);
    Flux<Category> findByPersonId(Long personId);
}
