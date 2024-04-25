package com.bezkoder.spring.security.postgresql.controllers.trainings.serializer;

import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.TrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.entity.TrainingEntity;

public class TrainingSerializer {

  public static TrainingDto convert(TrainingEntity trainingEntity){

    return TrainingDto.builder()
        .id(trainingEntity.getId())
        .workbooks_id(trainingEntity.getWorkbooks_id())
        .date(trainingEntity.getDate().toString())
        .notes(trainingEntity.getNotes())
        .build();
  }

}
