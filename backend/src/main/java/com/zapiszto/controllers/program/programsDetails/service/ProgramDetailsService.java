package com.zapiszto.controllers.program.programsDetails.service;

import com.zapiszto.controllers.program.programsDetails.dto.ProgramDetailsAssignedClientDto;
import com.zapiszto.controllers.program.programsDetails.dto.ProgramDetailsDto;
import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import com.zapiszto.controllers.program.programsDetails.repository.ProgramDetailsRepository;
import com.zapiszto.controllers.program.programsDetails.serializer.ProgramDetailsSerializer;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProgramDetailsService {
  @Autowired
  ProgramDetailsRepository programDetailsRepository;

  @Autowired
  ProgramDetailsSerializer programDetailsSerializer;

  public void updateProgramDetails(ProgramDetailsAssignedClientDto assignedClientDto) {
    UUID programId = UUID.fromString(assignedClientDto.getProgramId());
    UUID clientId = UUID.fromString(assignedClientDto.getAssignedClient());
    Optional<ProgramDetailsEntity> programDetailsEntityOptional = programDetailsRepository.findByUuid(programId);
    if (programDetailsEntityOptional.isPresent()) {
      ProgramDetailsEntity programDetailsEntity = programDetailsEntityOptional.get();
      programDetailsEntity.setAssignedClientId(clientId);
      programDetailsRepository.save(programDetailsEntity);
    } else {
      throw new RuntimeException("Program details not found for id: " + programId);
    }
  }

  public ProgramDetailsDto getProgramDetails(UUID programId) {
    Optional<ProgramDetailsEntity> programDetailsEntity = programDetailsRepository.findByUuid(programId);
    if (programDetailsEntity.isPresent()) {
      return programDetailsSerializer.convert(programDetailsEntity.get());
    } else {
      throw new RuntimeException("Program details not found for id: " + programId);
    }
  }
}
