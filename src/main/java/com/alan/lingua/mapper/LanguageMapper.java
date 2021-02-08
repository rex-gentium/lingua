package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.LanguageDto;
import com.alan.lingua.model.Language;
import com.alan.lingua.service.DictionaryService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses= DictionaryService.class)
@Component
public interface LanguageMapper {
    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    LanguageDto toDto(Language language);
}
