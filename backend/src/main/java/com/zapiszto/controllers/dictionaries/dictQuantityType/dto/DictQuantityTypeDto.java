package com.zapiszto.controllers.dictionaries.dictQuantityType.dto;

import java.util.UUID;

public record  DictQuantityTypeDto(
    UUID id,
    String dict,
    UUID dict_id,
    String name,
    String shortcut
) {}
