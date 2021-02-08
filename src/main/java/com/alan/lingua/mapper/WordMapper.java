package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.WordDto;
import com.alan.lingua.model.Word;
import com.alan.lingua.service.WordService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses= WordService.class)
@Component
public interface WordMapper {
    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    WordDto toDto(Word word);
}
