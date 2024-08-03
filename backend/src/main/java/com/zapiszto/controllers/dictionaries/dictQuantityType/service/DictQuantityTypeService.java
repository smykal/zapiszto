package com.zapiszto.controllers.dictionaries.dictQuantityType.service;

import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypeBasic.repository.DictQuantityTypeBasicRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.repository.DictQuantityTypePerUserRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.DictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dto.NewDictQuantityTypeDto;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypeBasic.entity.DictQuantityTypeBasicEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.serializer.DictQuantityTypeSerializer;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictQuantityTypeService {

  @Autowired
  DictQuantityTypeRepository dictQuantityTypeRepository;

  @Autowired
  DictQuantityTypeBasicRepository dictQuantityTypeBasicRepository;

  @Autowired
  DictQuantityTypePerUserRepository dictQuantityTypePerUserRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;


  @Transactional
  public void addDictQuantityType(NewDictQuantityTypeDto newDictUnitDto, Long userId) {
    var item = DictQuantityTypePerUserEntity.builder()
        .id(newDictUnitDto.getId())
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .user_id(userId)
        .build();

    DictQuantityTypePerUserEntity dictQuantityTypePerUserEntity = dictQuantityTypePerUserRepository.save(item);

    log.info("add new item to dict_QuantityType_per_user: id {}, value {}, shortcut {}, user {}",
        dictQuantityTypePerUserEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut(),
        userId);

    DictQuantityTypeEntity dictQuantityTypeEntity = DictQuantityTypeEntity.builder()
        .id(UUID.randomUUID())
        .dictQuantityTypePerUserEntity(dictQuantityTypePerUserEntity)
        .build();

    dictQuantityTypeRepository.save(dictQuantityTypeEntity);

    log.info("updated dict_QuantityType by new item with id: {}, dict_QuantityType_per_user_id: {} ",
        dictQuantityTypeEntity.getId(),
        dictQuantityTypeEntity.getDictQuantityTypePerUserEntity().getId());
  }

  @Transactional
  public void addDictQuantityType(NewDictQuantityTypeDto newDictUnitDto) {
    var item = DictQuantityTypeBasicEntity.builder()
        .name(newDictUnitDto.getName())
        .shortcut(newDictUnitDto.getShortcut())
        .build();

    DictQuantityTypeBasicEntity dictQuantityTypeBasicEntity = dictQuantityTypeBasicRepository.save(item);

    log.info("add new item to dict_QuantityType_basic: id {}, value {}, shortcut {}",
        dictQuantityTypeBasicEntity.getId(),
        newDictUnitDto.getName(),
        newDictUnitDto.getShortcut());

    DictQuantityTypeEntity dictQuantityTypeEntity = DictQuantityTypeEntity.builder()
        .dictQuantityTypeBasicEntity(dictQuantityTypeBasicEntity)
        .build();

    dictQuantityTypeRepository.save(dictQuantityTypeEntity);

    log.info("updated dict_QuantityType by new item with id: {}, dict_QuantityType_basic_id: {} ",
        dictQuantityTypeEntity.getId(),
        dictQuantityTypeEntity.getDictQuantityTypeBasicEntity().getId());
  }

  public List<DictQuantityTypeDto> getDictQuantityType(Long userId) {
    List<DictQuantityTypeEntity> all = dictQuantityTypeRepository.getAllForUser(userId);
    Languages lang = userDetailsRepository.userLanguage(userId);

    return all.stream().map(dictQuantityType -> DictQuantityTypeSerializer.convert(dictQuantityType, lang))
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictQuantityTypePerUser(Long userId, UUID itemToDelete) {
    try {
      dictQuantityTypeRepository.deleteDictQuantityTypePerUser(itemToDelete);
      dictQuantityTypeRepository.deleteDictQuantityTypePerUser(itemToDelete, userId);
      log.info("deleted dict_quantity_type_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting quantity type: {}", e.getMessage());
      throw new RuntimeException("Cannot delete quantity type due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictQuantityTypeBasic(Long userId, UUID itemToDelete) {
    try {
      dictQuantityTypeRepository.deleteDictQuantityType(itemToDelete);
      dictQuantityTypeRepository.deleteDictQuantityTypeBasic(itemToDelete);
      log.info("deleted dict_quantity_type_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict quantity type: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict quantity type due to data integrity violation");
    }
  }

}
