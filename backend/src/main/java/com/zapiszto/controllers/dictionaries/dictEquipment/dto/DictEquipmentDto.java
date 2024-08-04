package com.zapiszto.controllers.dictionaries.dictEquipment.dto;

import java.util.UUID;

public record DictEquipmentDto(
    UUID id,
    String dict,
    UUID dict_id,
    String name) {}
