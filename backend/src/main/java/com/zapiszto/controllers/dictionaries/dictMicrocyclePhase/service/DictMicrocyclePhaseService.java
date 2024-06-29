package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.service;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhaseBasic.entity.DictMicrocyclePhaseBasicEntity;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhaseBasic.repository.DictMicrocyclePhaseBasicRepository;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.entity.DictMicrocyclePhasePerUserEntity;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.repository.DictMicrocyclePhasePerUserRepository;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dto.DictMicrocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dto.NewDictMicrocyclePhaseDto;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.entity.DictMicrocyclePhaseEntity;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.repository.DictMicrocyclePhaseRepository;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.serializer.DictMicrocyclePhaseSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictMicrocyclePhaseService {

  @Autowired
  DictMicrocyclePhaseRepository dictMicrocyclePhaseRepository;

  @Autowired
  DictMicrocyclePhaseBasicRepository dictMicrocyclePhaseBasicRepository;

  @Autowired
  DictMicrocyclePhasePerUserRepository dictMicrocyclePhasePerUserRepository;

  @Transactional
  public void addDictUnit(NewDictMicrocyclePhaseDto newDictUnitDto, Long userId) {
    var item = DictMicrocyclePhasePerUserEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .userId(userId)
        .build();

    DictMicrocyclePhasePerUserEntity dictMicrocyclePhasePerUserEntity = dictMicrocyclePhasePerUserRepository.save(item);

    log.info("add new item to dict_MicrocyclePhase_per_user: id {}, value {}, shortcut {}, user {}",
        dictMicrocyclePhasePerUserEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut(),
        userId);

    DictMicrocyclePhaseEntity dictMicrocyclePhaseEntity = DictMicrocyclePhaseEntity.builder()
        .dictMicrocyclePhasePerUserEntity(dictMicrocyclePhasePerUserEntity)
        .build();

    dictMicrocyclePhaseRepository.save(dictMicrocyclePhaseEntity);

    log.info("updated dict_MicrocyclePhase by new item with id: {}, dict_MicrocyclePhase_per_user_id: {} ",
        dictMicrocyclePhaseEntity.getId(),
        dictMicrocyclePhaseEntity.getDictMicrocyclePhasePerUserEntity().getId());
  }

  @Transactional
  public void addDictUnit(NewDictMicrocyclePhaseDto newDictUnitDto) {
    var item = DictMicrocyclePhaseBasicEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .build();

    DictMicrocyclePhaseBasicEntity dictMicrocyclePhaseBasicEntity = dictMicrocyclePhaseBasicRepository.save(item);

    log.info("add new item to dict_MicrocyclePhase_basic: id {}, value {}, shortcut {}",
        dictMicrocyclePhaseBasicEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut());

    DictMicrocyclePhaseEntity dictMicrocyclePhaseEntity = DictMicrocyclePhaseEntity.builder()
        .dictMicrocyclePhaseBasicEntity(dictMicrocyclePhaseBasicEntity)
        .build();

    dictMicrocyclePhaseRepository.save(dictMicrocyclePhaseEntity);

    log.info("updated dict_MicrocyclePhase by new item with id: {}, dict_MicrocyclePhase_basic_id: {} ",
        dictMicrocyclePhaseEntity.getId(),
        dictMicrocyclePhaseEntity.getDictMicrocyclePhaseBasicEntity().getId());
  }

  public List<DictMicrocyclePhaseDto> getDictMicrocyclePhase(Long userId) {
    List<DictMicrocyclePhaseEntity> all = dictMicrocyclePhaseRepository.getAllForUser(userId);

    return all.stream().map(DictMicrocyclePhaseSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictMicrocyclePhasePerUser(Long userId, int itemToDelete) {
    try {
      dictMicrocyclePhaseRepository.deleteDictMicrocyclePhasePerUser(itemToDelete);
      dictMicrocyclePhaseRepository.deleteDictMicrocyclePhasePerUser(itemToDelete, userId);
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
      dictMicrocyclePhaseRepository.deleteDictMicrocyclePhase(itemToDelete);
      dictMicrocyclePhaseRepository.deleteDictMicrocyclePhaseBasic(itemToDelete);
      log.info("deleted dict_quantity_type_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict quantity type: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict quantity type due to data integrity violation");
    }
  }
}
