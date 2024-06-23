package com.zapiszto.controllers.program.programs.serializer;

import com.zapiszto.controllers.program.programs.dto.ProgramDto;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import java.time.format.DateTimeFormatter;
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
        .id(programEntity.getId().toString())
        .programName(programEntity.getName())
        .createDate(programEntity.getInsert_date().format(formatter))
        .createdBy(programEntity.getTrainerId().toString())
        .build();
  }


}
