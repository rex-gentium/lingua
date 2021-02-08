package com.alan.lingua.dto.response;

import lombok.Data;

@Data
public class TranslationDto {
    private Long id;
    private WordDto sourceWord;
    private WordDto targetWord;
    private PersonDto author;
}
