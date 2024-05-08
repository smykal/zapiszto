package com.zapiszto.controllers.dictUnits.service;

import com.zapiszto.controllers.dictUnits.dictUnitsBasic.entity.DictUnitsBasicEntity;
import com.zapiszto.controllers.dictUnits.dictUnitsBasic.repository.DictUnitsBasicRepository;
import com.zapiszto.controllers.dictUnits.dictUnitsPerUser.entity.DictUnitsPerUserEntity;
import com.zapiszto.controllers.dictUnits.dictUnitsPerUser.repository.DictUnitsPerUserRepository;
import com.zapiszto.controllers.dictUnits.dto.DictUnitsDto;
import com.zapiszto.controllers.dictUnits.dto.NewDictUnitDto;
import com.zapiszto.controllers.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.controllers.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.dictUnits.serializer.DictUnitsSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictUnitsService {

  @Autowired
  DictUnitsRepository dictUnitsRepository;

  @Autowired
  DictUnitsBasicRepository dictUnitsBasicRepository;

  @Autowired
  DictUnitsPerUserRepository dictUnitsPerUserRepository;

  @Transactional
  public void addDictUnit(NewDictUnitDto newDictUnitDto, Long userId) {
    var item = DictUnitsPerUserEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .user_id(userId)
        .build();

    DictUnitsPerUserEntity dictUnitsPerUserEntity = dictUnitsPerUserRepository.save(item);

    log.info("add new item to dict_units_per_user: id {}, value {}, shortcut {}, user {}",
        dictUnitsPerUserEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut(),
        userId);

    DictUnitsEntity dictUnitsEntity = DictUnitsEntity.builder()
        .dictUnitsPerUserEntity(dictUnitsPerUserEntity)
        .build();

    dictUnitsRepository.save(dictUnitsEntity);

    log.info("updated dict_units by new item with id: {}, dict_units_per_user_id: {} ",
        dictUnitsEntity.getId(),
        dictUnitsEntity.getDictUnitsPerUserEntity().getId());
  }

  @Transactional
  public void addDictUnit(NewDictUnitDto newDictUnitDto) {
    var item = DictUnitsBasicEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .build();

    DictUnitsBasicEntity dictUnitsBasicEntity = dictUnitsBasicRepository.save(item);

    log.info("add new item to dict_units_basic: id {}, value {}, shortcut {}",
        dictUnitsBasicEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut());

    DictUnitsEntity dictUnitsEntity = DictUnitsEntity.builder()
        .dictUnitsBasicEntity(dictUnitsBasicEntity)
        .build();

    dictUnitsRepository.save(dictUnitsEntity);

    log.info("updated dict_units by new item with id: {}, dict_units_basic_id: {} ",
        dictUnitsEntity.getId(),
        dictUnitsEntity.getDictUnitsBasicEntity().getId());
  }

  public List<DictUnitsDto> getDictUnits(Long userId) {
    List<DictUnitsEntity> all = dictUnitsRepository.getAllForUser(userId);

    return all.stream().map(DictUnitsSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictUnitPerUser(Long userId, int itemToDelete) {
    try {
      dictUnitsRepository.deleteDictUnitPerUser(itemToDelete);
      dictUnitsRepository.deleteDictUnitPerUser(itemToDelete, userId);
      log.info("deleted dict_unit_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict unit: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict unit due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictUnitBasic(Long userId, int itemToDelete) {
    try {
      dictUnitsRepository.deleteDictUnit(itemToDelete);
      dictUnitsRepository.deleteDictUnitBasic(itemToDelete);
      log.info("deleted dict_unit_basic with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict unit: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict unit due to data integrity violation");
    }
  }
}
