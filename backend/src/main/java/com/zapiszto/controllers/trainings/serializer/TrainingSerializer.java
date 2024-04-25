package com.zapiszto.controllers.trainings.serializer;

import com.zapiszto.controllers.trainings.dto.TrainingDto;
import com.zapiszto.controllers.trainings.entity.TrainingEntity;

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
