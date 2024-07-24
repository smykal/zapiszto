package com.zapiszto.controllers.program.programs.service;

import com.zapiszto.controllers.program.programs.serializer.ProgramsSerializer;
import com.zapiszto.controllers.program.programs.dto.NewProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramNameDto;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import com.zapiszto.controllers.program.programs.repository.ProgramsRepository;
import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import com.zapiszto.controllers.program.programsDetails.repository.ProgramDetailsRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ProgramsService {

  @Autowired
  ProgramsRepository programsRepository;

  @Autowired
  ProgramDetailsRepository programDetailsRepository;

  @Autowired
  ProgramsSerializer programsSerializer;

  public List<ProgramDto> getPrograms(long trainerId) {
    List<Object[]> allByTrainerId = programsRepository.getAllByTrainerId(trainerId);
    return allByTrainerId.stream().map(programsSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public void addProgram(NewProgramDto newProgramDto, long trainerId) {
    UUID uuid = UUID.fromString(newProgramDto.getId());
    ProgramEntity programEntity = ProgramEntity.builder()
        .id(uuid)
        .name(newProgramDto.getProgramName())
        .trainerId(trainerId)
        .insert_date(ZonedDateTime.now())
        .build();
    programsRepository.save(programEntity);

    ProgramDetailsEntity programDetailsEntity = ProgramDetailsEntity.builder()
        .id(uuid)
        .build();
    programDetailsRepository.save(programDetailsEntity);
  }

  @Transactional
  public void deleteProgram(UUID programId) {
    programsRepository.deleteById(programId);
    log.info("Program with ID {} and its related entities have been deleted", programId);
  }

  @Modifying
  @Transactional
  public void updateProgramName(String id, ProgramNameDto programNameDto) {
    ProgramEntity programEntity = programsRepository.findByUuid(UUID.fromString(id))
        .orElseThrow(() -> new RuntimeException("Program not found"));
    programEntity.setName(programNameDto.getProgramName());
    programsRepository.save(programEntity);
  }
}
