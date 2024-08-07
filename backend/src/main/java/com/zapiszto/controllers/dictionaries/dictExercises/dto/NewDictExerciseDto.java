package com.zapiszto.controllers.dictionaries.dictExercises.dto;

import java.util.HashMap;
import java.util.UUID;

public record NewDictExerciseDto(
    UUID id,
    HashMap<String, String> name,
    Long categoryId) {}
