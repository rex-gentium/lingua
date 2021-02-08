create view translation_view as
    select
        t.id as id,
        w1.id as source_word_id,
        w1.language_id as source_language_id,
        w1.text as source_word_text,
        w2.id as target_word_id,
        w2.language_id as target_language_id,
        w2.text as target_word_text,
        p.id as author_id,
        p.name as author_name
    from
        translation t
        inner join word w1 on w1.id = t.source_word_id
        inner join word w2 on w2.id = t.target_word_id
        left join person p on p.id = t.author_id;
