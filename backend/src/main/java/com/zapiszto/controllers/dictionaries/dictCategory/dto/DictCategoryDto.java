package com.zapiszto.controllers.dictionaries.dictCategory.dto;

public record DictCategoryDto(
    long id,
    String dict,
    long dict_id,
    String name,
    String description
) {}
