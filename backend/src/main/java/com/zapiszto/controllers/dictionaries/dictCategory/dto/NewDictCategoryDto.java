package com.zapiszto.controllers.dictionaries.dictCategory.dto;

import java.util.HashMap;

public record NewDictCategoryDto(
    HashMap<String, String> name,
    HashMap<String, String> description) {
}
