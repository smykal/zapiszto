package com.zapiszto.controllers.dictionaries.dictBodyTest.dto;

import java.util.HashMap;

public record NewDictBodyTestDto(
    HashMap<String, String> name,
    HashMap<String, String> description,
    HashMap<String, String> purpose) {}
