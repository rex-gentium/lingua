package com.alan.lingua.controller;

import com.alan.lingua.dto.response.LanguageDto;
import com.alan.lingua.service.DictionaryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/api/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/language", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<LanguageDto>>> getLanguages() {
        return dictionaryService.getLanguages()
                .collectList()
                .map(ResponseEntity::ok);
    }
}
