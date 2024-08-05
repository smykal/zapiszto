package com.zapiszto.controllers.dictionaries.dictUnits.dto;

import java.util.UUID;


public record DictUnitsDto(
    UUID id,
    String dict,
    UUID dict_id,
    String name,
    String shortcut
) {}
