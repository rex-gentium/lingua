package com.alan.lingua.dto.response;

import lombok.Data;

@Data
public class WordDto {
    private Long id;
    private Long languageId;
    private String text;
}
