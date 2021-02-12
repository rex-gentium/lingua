package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.CategoryDto;
import com.alan.lingua.model.Category;
import com.alan.lingua.service.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = CategoryService.class)
@Component
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category category);
}
