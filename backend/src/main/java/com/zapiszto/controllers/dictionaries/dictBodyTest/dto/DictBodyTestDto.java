package com.zapiszto.controllers.dictionaries.dictBodyTest.dto;

import java.util.UUID;

public record DictBodyTestDto(
    UUID id,
    String dict,
    String dict_id,
    String name,
    String description,
    String purpose
) {}
