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
    return new ProgramDto(
        programEntity.getId(),
        programEntity.getName(),
        programEntity.getInsert_date()
            .format(formatter),
        programEntity.getTrainerId(),
        null);
  }

  public ProgramDto convert(Object[] programObject) {
    return new ProgramDto(
        (UUID) programObject[0],
        (String) programObject[3],
        ((Timestamp) programObject[2]).toString(),
        (Long) programObject[1],
        (String) programObject[4]);
  }
}
