package com.zapiszto.controllers.dictionaries.dictUnits.dto;

import java.util.HashMap;
import java.util.UUID;

public record NewDictUnitDto(
    UUID id,
    HashMap<String, String> name,
    HashMap<String, String> shortcut) {
}
