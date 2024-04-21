package com.bezkoder.spring.security.postgresql.controllers.trainings.service;

import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.TrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.TrainingNotesDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.repository.TrainingsRepository;
import com.bezkoder.spring.security.postgresql.controllers.trainings.dto.NewTrainingDto;
import com.bezkoder.spring.security.postgresql.controllers.trainings.entity.TrainingEntity;
import com.bezkoder.spring.security.postgresql.controllers.trainings.serializer.TrainingSerializer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingsService {

  @Autowired
  TrainingsRepository trainingsRepository;

  public void addTraining(NewTrainingDto newTrainingDto) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(newTrainingDto.getDate(), DateTimeFormatter.ISO_DATE_TIME);

    TrainingEntity trainingEntity = TrainingEntity.builder()
        .workbooks_id(newTrainingDto.getWorkbook_id())
        .date(zonedDateTime)
        .build();

    trainingsRepository.save(trainingEntity);
    log.info("dodano nowy trening dla workbook_id: {}, z datą: {}", newTrainingDto.getWorkbook_id(), zonedDateTime);
  }

  public List<TrainingDto> getTrainings(int workbookId, Long userId) {
    List<TrainingEntity> trainings = trainingsRepository.getTrainings(userId, workbookId);
    return trainings.stream()
        .map(TrainingSerializer::convert)
        .collect(Collectors.toList());
  }

  public void updateTrainingNotes(TrainingNotesDto trainingNotesDto){
    TrainingEntity trainingEntity = TrainingEntity.builder()
        .id(trainingNotesDto.getId())
        .notes(trainingNotesDto.getNotes())
        .build();
    trainingsRepository.save(trainingEntity);

  }
}
