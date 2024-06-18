package com.zapiszto.controllers.programs.serializer;

import com.zapiszto.controllers.programs.dto.ProgramDto;
import com.zapiszto.controllers.programs.entity.ProgramEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProgramsSerializer {

  public ProgramDto convert(ProgramEntity programEntity) {
    return ProgramDto.builder()
        .id(programEntity.getId().toString())
        .programName(programEntity.getName())
        .createDate(programEntity.getInsert_date().toString())
        .build();
  }


}
