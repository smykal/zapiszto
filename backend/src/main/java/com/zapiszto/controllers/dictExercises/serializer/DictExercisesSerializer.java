package com.zapiszto.controllers.dictExercises.serializer;

import com.bezkoder.spring.security.postgresql.controllers.dictExercises.dto.DictExercisesDto;
import com.bezkoder.spring.security.postgresql.controllers.dictExercises.entity.DictExercisesEntity;

public class DictExercisesSerializer {

  private static final String PER_USER = "PER_USER";
  private static final String BASIC = "BASIC";
  public static DictExercisesDto convert(DictExercisesEntity dictExercisesEntity) {

    if (dictExercisesEntity.getDictExercisesBasicEntity() != null) {
      return DictExercisesDto.builder()
          .id(dictExercisesEntity.getId())
          .dict(BASIC)
          .dict_id( dictExercisesEntity.getDictExercisesBasicEntity().getId())
          .name(dictExercisesEntity.getDictExercisesBasicEntity().getName())
          .build();
    } else {
      return DictExercisesDto.builder()
          .id(dictExercisesEntity.getId())
          .dict(PER_USER)
          .dict_id(dictExercisesEntity.getDictExercisesPerUserEntity().getId())
          .name(dictExercisesEntity.getDictExercisesPerUserEntity().getName())
          .build();
    }
  }
}
