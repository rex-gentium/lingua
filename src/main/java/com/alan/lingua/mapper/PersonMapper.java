package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.PersonDto;
import com.alan.lingua.model.Person;
import com.alan.lingua.service.PersonService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses= PersonService.class)
@Component
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto toDto(Person person);
}
