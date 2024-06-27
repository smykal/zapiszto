package com.zapiszto.controllers.program.macrocycle.service;

import com.zapiszto.controllers.program.macrocycle.dto.MacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.dto.NewMacrocycleDto;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.macrocycle.repository.MacrocycleRepository;
import com.zapiszto.controllers.program.macrocycle.serializer.MacrocycleSerializer;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MacrocycleService {
  @Autowired
  MacrocycleRepository macrocycleRepository;

  @Autowired
  MacrocycleSerializer macrocycleSerializer;

  public void addMacrocycle(NewMacrocycleDto newMacrocycleDto) {
    UUID id = UUID.fromString(newMacrocycleDto.getId());
    UUID programId = UUID.fromString(newMacrocycleDto.getProgramId());

    MacrocycleEntity macrocycleEntity = MacrocycleEntity.builder()
        .id(id)
        .programId(programId)
        .duration(newMacrocycleDto.getDuration())
        .durationLeft(newMacrocycleDto.getDuration()*4)
        .build();
    macrocycleRepository.save(macrocycleEntity);
    log.info("dodano nowy mezocykl o id {}", id);
  }

  public MacrocycleDto getMacrocycle(UUID programId) {

    MacrocycleEntity macrocycleByProgramId = macrocycleRepository.getMacrocycleByProgramId(programId);
    return macrocycleSerializer.convert(macrocycleByProgramId);
  }
}
