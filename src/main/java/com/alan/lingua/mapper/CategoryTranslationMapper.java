package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.CategoryTranslationDto;
import com.alan.lingua.model.CategoryTranslation;
import com.alan.lingua.service.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = CategoryService.class)
@Component
public interface CategoryTranslationMapper {
    CategoryTranslationMapper INSTANCE = Mappers.getMapper(CategoryTranslationMapper.class);

    CategoryTranslationDto toDto(CategoryTranslation categoryTranslation);
}
