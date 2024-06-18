package com.zapiszto.controllers.programs.service;

import com.zapiszto.controllers.programs.dto.NewProgramDto;
import com.zapiszto.controllers.programs.dto.ProgramDto;
import com.zapiszto.controllers.programs.entity.ProgramEntity;
import com.zapiszto.controllers.programs.repository.ProgramsRepository;
import com.zapiszto.controllers.programs.serializer.ProgramsSerializer;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProgramsService {

  @Autowired
  ProgramsRepository programsRepository;

  @Autowired
  ProgramsSerializer programsSerializer;

  public List<ProgramDto> getPrograms(long trainerId) {
    List<ProgramEntity> allByTrainerId = programsRepository.getAllByTrainerId(trainerId);
    return allByTrainerId.stream().map(programsSerializer::convert)
        .collect(Collectors.toList());
  }

  public void addProgram(NewProgramDto newProgramDto, long trainerId) {
    ProgramEntity programEntity = ProgramEntity.builder()
        .id(UUID.fromString(newProgramDto.getId()))
        .name(newProgramDto.getProgramName())
        .trainerId(trainerId)
        .insert_date(ZonedDateTime.now())
        .build();
    programsRepository.save(programEntity);
  }


  public void updateProgram(ProgramDto programDto) {
    var existingProgram = programsRepository.findByUuid(UUID.fromString(programDto.getId()));
    existingProgram.setName(programDto.getProgramName());
    existingProgram.setInsert_date(ZonedDateTime.parse(programDto.getCreateDate()));
    programsRepository.save(existingProgram);
  }

  public void deleteProgram(UUID id) {
    if (!programsRepository.existsById(id)) {
      throw new RuntimeException("Program not found");
    }
    programsRepository.deleteById(id);
  }

}
