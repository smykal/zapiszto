package com.zapiszto.controllers.dictionaries.dictExercises.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictExercises.dto.DictExercisesDto;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.utilities.translator.Translations;

public class DictExercisesSerializer extends Translations implements SerializerCommon {


  public static DictExercisesDto convert(DictExercisesEntity dictExercisesEntity, Languages language) {
    boolean isExerciseBasic = dictExercisesEntity.getDictExercisesBasicEntity() != null;

    if (isExerciseBasic) {
      boolean isCategoryBasic = dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryBasicEntity() != null;

      if (isCategoryBasic) {
        var exerciseName = dictExercisesEntity.getDictExercisesBasicEntity().getName();
        var categoryName = dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryBasicEntity().getName();
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(BASIC)
            .dict_id( dictExercisesEntity.getDictExercisesBasicEntity().getId())
            .name(translate(exerciseName, language))
            .category_type(BASIC)
            .category_id(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getId())
            .category_name(translate(categoryName, language))
            .build();
      } else {
        var exerciseName = dictExercisesEntity.getDictExercisesBasicEntity().getName();
        var categoryName = dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryPerUserEntity().getName();
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(BASIC)
            .dict_id( dictExercisesEntity.getDictExercisesBasicEntity().getId())
            .name(translate(exerciseName , language))
            .category_type(PER_USER)
            .category_id(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getId())
            .category_name(translate(categoryName, language))
            .build();
      }
    } else {
      boolean isCategoryPerUser = dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryPerUserEntity() != null;

      if (isCategoryPerUser) {
        var exerciseName = dictExercisesEntity.getDictExercisesPerUserEntity().getName();
        var categoryName = dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryPerUserEntity().getName();
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(PER_USER)
            .dict_id(dictExercisesEntity.getDictExercisesPerUserEntity().getId())
            .name(translate(exerciseName, language))
            .category_type(PER_USER)
            .category_id(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getId())
            .category_name(translate(categoryName, language))
            .build();
      } else {
        var exerciseName = dictExercisesEntity.getDictExercisesPerUserEntity().getName();
        var categoryName = dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryBasicEntity().getName();
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(PER_USER)
            .dict_id(dictExercisesEntity.getDictExercisesPerUserEntity().getId())
            .name(translate( exerciseName, language))
            .category_type(BASIC)
            .category_id(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getId())
            .category_name(translate(categoryName, language))
            .build();
      }
    }
  }
}
