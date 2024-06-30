package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.service;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhaseBasic.entity.DictMesocyclePhaseBasicEntity;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhaseBasic.repository.DictMesocyclePhaseBasicRepository;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhasePerUser.entity.DictMesocyclePhasePerUserEntity;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhasePerUser.repository.DictMesocyclePhasePerUserRepository;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dto.DictMesocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dto.NewDictMesocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity.DictMesocyclePhaseEntity;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.repository.DictMesocyclePhaseRepository;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.serializer.DictMesocyclePhaseSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictMesocyclePhaseService {

  @Autowired
  DictMesocyclePhaseRepository dictMesocyclePhaseRepository;

  @Autowired
  DictMesocyclePhaseBasicRepository dictMesocyclePhaseBasicRepository;

  @Autowired
  DictMesocyclePhasePerUserRepository dictMesocyclePhasePerUserRepository;

  @Transactional
  public void addDictUnit(NewDictMesocyclePhaseDto newDictUnitDto, Long userId) {
    var item = DictMesocyclePhasePerUserEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .userId(userId)
        .build();

    DictMesocyclePhasePerUserEntity dictMesocyclePhasePerUserEntity = dictMesocyclePhasePerUserRepository.save(item);

    log.info("add new item to dict_MicrocyclePhase_per_user: id {}, value {}, shortcut {}, user {}",
        dictMesocyclePhasePerUserEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut(),
        userId);

    DictMesocyclePhaseEntity dictMesocyclePhaseEntity = DictMesocyclePhaseEntity.builder()
        .dictMesocyclePhasePerUserEntity(dictMesocyclePhasePerUserEntity)
        .build();

    dictMesocyclePhaseRepository.save(dictMesocyclePhaseEntity);

    log.info("updated dict_MicrocyclePhase by new item with id: {}, dict_MicrocyclePhase_per_user_id: {} ",
        dictMesocyclePhaseEntity.getId(),
        dictMesocyclePhaseEntity.getDictMesocyclePhasePerUserEntity().getId());
  }

  @Transactional
  public void addDictUnit(NewDictMesocyclePhaseDto newDictUnitDto) {
    var item = DictMesocyclePhaseBasicEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .build();

    DictMesocyclePhaseBasicEntity dictMesocyclePhaseBasicEntity = dictMesocyclePhaseBasicRepository.save(item);

    log.info("add new item to dict_MicrocyclePhase_basic: id {}, value {}, shortcut {}",
        dictMesocyclePhaseBasicEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut());

    DictMesocyclePhaseEntity dictMesocyclePhaseEntity = DictMesocyclePhaseEntity.builder()
        .dictMesocyclePhaseBasicEntity(dictMesocyclePhaseBasicEntity)
        .build();

    dictMesocyclePhaseRepository.save(dictMesocyclePhaseEntity);

    log.info("updated dict_MicrocyclePhase by new item with id: {}, dict_MicrocyclePhase_basic_id: {} ",
        dictMesocyclePhaseEntity.getId(),
        dictMesocyclePhaseEntity.getDictMesocyclePhaseBasicEntity().getId());
  }

  public List<DictMesocyclePhaseDto> getDictMicrocyclePhase(Long userId) {
    List<DictMesocyclePhaseEntity> all = dictMesocyclePhaseRepository.getAllForUser(userId);

    return all.stream().map(DictMesocyclePhaseSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictMicrocyclePhasePerUser(Long userId, int itemToDelete) {
    try {
      dictMesocyclePhaseRepository.deleteDictMicrocyclePhasePerUser(itemToDelete);
      dictMesocyclePhaseRepository.deleteDictMicrocyclePhasePerUser(itemToDelete, userId);
      log.info("deleted dict_quantity_type_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting quantity type: {}", e.getMessage());
      throw new RuntimeException("Cannot delete quantity type due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictMicrocyclePhaseBasic(Long userId, int itemToDelete) {
    try {
      dictMesocyclePhaseRepository.deleteDictMicrocyclePhase(itemToDelete);
      dictMesocyclePhaseRepository.deleteDictMicrocyclePhaseBasic(itemToDelete);
      log.info("deleted dict_quantity_type_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict quantity type: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict quantity type due to data integrity violation");
    }
  }
}
