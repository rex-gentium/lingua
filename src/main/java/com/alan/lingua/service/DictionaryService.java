package com.alan.lingua.service;

import com.alan.lingua.dto.response.LanguageDto;
import com.alan.lingua.mapper.LanguageMapper;
import com.alan.lingua.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DictionaryService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Autowired
    public DictionaryService(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    public Flux<LanguageDto> getLanguages() {
        return languageRepository.findAll()
                .map(languageMapper::toDto);
    }
}
