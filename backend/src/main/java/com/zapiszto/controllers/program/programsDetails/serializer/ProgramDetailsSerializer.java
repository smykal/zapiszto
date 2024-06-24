package com.zapiszto.controllers.program.programsDetails.serializer;

import com.zapiszto.controllers.program.programsDetails.dto.ProgramDetailsDto;
import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProgramDetailsSerializer {
  public ProgramDetailsDto convert(ProgramDetailsEntity programDetailsEntity) {
    return ProgramDetailsDto.builder()
        .programId(programDetailsEntity.getId())
        .assignedClient(programDetailsEntity.getAssignedClientId())
        .build();
  }
}
