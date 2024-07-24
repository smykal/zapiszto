package com.zapiszto.controllers.program.programs.serializer;

import com.zapiszto.controllers.program.programs.dto.ProgramDto;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProgramsSerializer {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public ProgramDto convert(ProgramEntity programEntity) {
    return ProgramDto.builder()
        .id(programEntity.getId())
        .programName(programEntity.getName())
        .createDate(programEntity.getInsert_date().format(formatter))
        .createdBy(programEntity.getTrainerId())
        .build();
  }

  public ProgramDto convert(Object[] programObject) {
    return ProgramDto.builder()
        .id((UUID) programObject[0])
        .programName((String) programObject[3])
        .createDate(((Timestamp) programObject[2]).toString())
        .createdBy((Long) programObject[1])
        .clientName((String) programObject[4])
        .build();
  }
}
