package com.zapiszto.controllers.dictionaries.dictSessionPart.dto;

import java.util.UUID;


public record DictSessionPartDto(
    UUID id,
    String dict,
    UUID dict_id,
    String name
) {}
