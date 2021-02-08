package com.alan.lingua.mapper;

import com.alan.lingua.dto.response.PersonDto;
import com.alan.lingua.dto.response.TranslationDto;
import com.alan.lingua.dto.response.WordDto;
import com.alan.lingua.model.*;
import com.alan.lingua.service.TranslationService;
import com.alan.lingua.service.WordService;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses= TranslationService.class)
@Component
public abstract class TranslationMapper {
    public TranslationDto toDto(Translation translation, Word source, Word target, Person author) {
        TranslationDto res = new TranslationDto();
        res.setAuthor(PersonMapper.INSTANCE.toDto(author));
        res.setSourceWord(WordMapper.INSTANCE.toDto(source));
        res.setTargetWord(WordMapper.INSTANCE.toDto(target));
        res.setId(translation.getId());
        return res;
    }

    public TranslationDto toDto(TranslationView translationView) {
        TranslationDto res = new TranslationDto();
        val author = new PersonDto();
        author.setId(translationView.getAuthorId());
        author.setName(translationView.getAuthorName());
        res.setAuthor(author.getId() != null ? author : null);
        val srcWord = new WordDto();
        srcWord.setId(translationView.getSourceWordId());
        srcWord.setLanguageId(translationView.getSourceLanguageId());
        srcWord.setText(translationView.getSourceWordText());
        res.setSourceWord(srcWord);
        val tgtWord = new WordDto();
        tgtWord.setId(translationView.getTargetWordId());
        tgtWord.setLanguageId(translationView.getTargetLanguageId());
        tgtWord.setText(translationView.getTargetWordText());
        res.setTargetWord(tgtWord);
        res.setId(translationView.getId());
        return res;
    }
}
