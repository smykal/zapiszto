package com.zapiszto.controllers.dictionaries.dictExercises.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictExercises.dto.DictExercisesDto;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;

public class DictExercisesSerializer implements SerializerCommon {


  public static DictExercisesDto convert(DictExercisesEntity dictExercisesEntity) {
    boolean isExerciseBasic = dictExercisesEntity.getDictExercisesBasicEntity() != null;

    if (isExerciseBasic) {
      boolean isCategoryBasic = dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryBasicEntity() != null;

      if (isCategoryBasic) {
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(BASIC)
            .dict_id( dictExercisesEntity.getDictExercisesBasicEntity().getId())
            .name(dictExercisesEntity.getDictExercisesBasicEntity().getName())
            .category_type(BASIC)
            .category_id(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getId())
            .category_name(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryBasicEntity().getName())
            .build();
      } else {
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(BASIC)
            .dict_id( dictExercisesEntity.getDictExercisesBasicEntity().getId())
            .name(dictExercisesEntity.getDictExercisesBasicEntity().getName())
            .category_type(PER_USER)
            .category_id(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getId())
            .category_name(dictExercisesEntity.getDictExercisesBasicEntity().getDictCategoryEntity().getDictCategoryPerUserEntity().getName())
            .build();
      }
    } else {
      boolean isCategoryPerUser = dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryPerUserEntity() != null;

      if (isCategoryPerUser) {
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(PER_USER)
            .dict_id(dictExercisesEntity.getDictExercisesPerUserEntity().getId())
            .name(dictExercisesEntity.getDictExercisesPerUserEntity().getName())
            .category_type(PER_USER)
            .category_id(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getId())
            .category_name(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryPerUserEntity().getName())
            .build();
      } else {
        return DictExercisesDto.builder()
            .id(dictExercisesEntity.getId())
            .dict(PER_USER)
            .dict_id(dictExercisesEntity.getDictExercisesPerUserEntity().getId())
            .name(dictExercisesEntity.getDictExercisesPerUserEntity().getName())
            .category_type(BASIC)
            .category_id(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getId())
            .category_name(dictExercisesEntity.getDictExercisesPerUserEntity().getDictCategoryEntity().getDictCategoryBasicEntity().getName())
            .build();
      }
    }
  }
}
