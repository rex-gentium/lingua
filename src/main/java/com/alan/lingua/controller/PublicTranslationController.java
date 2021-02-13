package com.alan.lingua.controller;

import com.alan.lingua.dto.response.TranslationDto;
import com.alan.lingua.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/translation")
public class PublicTranslationController {
    private final TranslationService translationService;

    @Autowired
    public PublicTranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TranslationDto>>> getTranslations(@RequestParam("word") String word,
                                                                      @RequestParam("src_lang") String sourceLanguageCode,
                                                                      @RequestParam("tgt_lang") String targetLanguageCode) {
        return translationService.getTranslations(word, sourceLanguageCode, targetLanguageCode)
                .collectList()
                .map(ResponseEntity::ok);
    }
}
