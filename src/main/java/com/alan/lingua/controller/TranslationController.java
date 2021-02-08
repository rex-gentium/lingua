package com.alan.lingua.controller;

import com.alan.lingua.dto.response.TranslationDto;
import com.alan.lingua.model.Translation;
import com.alan.lingua.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/translation")
public class TranslationController {
    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TranslationDto>> addTranslation(Principal principal, @RequestBody Map<String, String> translationPair) {
        return translationService.addTranslation(principal, translationPair)
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/mine", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TranslationDto>>> getMyTranslations(Principal principal) {
        return translationService.getTranslations(principal)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TranslationDto>>> getTranslations(Principal principal,
                                                                      @RequestParam("word") String word,
                                                                      @RequestParam("src_lang") String sourceLanguageCode,
                                                                      @RequestParam("tgt_lang") String targetLanguageCode) {
        return translationService.getTranslations(principal, word, sourceLanguageCode, targetLanguageCode)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TranslationDto>> addTranslation(Principal principal, @RequestBody Long id) {
        return translationService.addTranslation(principal, id)
                .map(ResponseEntity::ok);
    }
}
