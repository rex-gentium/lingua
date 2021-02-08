alter table word
    add constraint word_language_text_pk
        unique (language_id, "text");