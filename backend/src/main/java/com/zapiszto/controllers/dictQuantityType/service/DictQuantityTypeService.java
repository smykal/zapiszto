package com.zapiszto.controllers.dictQuantityType.service;

import com.zapiszto.controllers.dictQuantityType.dictQuantityTypeBasic.repository.DictQuantityTypeBasicRepository;
import com.zapiszto.controllers.dictQuantityType.dictQuantityTypePerUser.repository.DictQuantityTypePerUserRepository;
import com.zapiszto.controllers.dictQuantityType.dto.DictQuantityTypeDto;
import com.zapiszto.controllers.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
import com.zapiszto.controllers.dictQuantityType.dto.NewDictQuantityTypeDto;
import com.zapiszto.controllers.dictQuantityType.dictQuantityTypeBasic.entity.DictQuantityTypeBasicEntity;
import com.zapiszto.controllers.dictQuantityType.serializer.DictQuantityTypeSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


  @Transactional
  public void addDictUnit(NewDictQuantityTypeDto newDictUnitDto, Long userId) {
    var item = DictQuantityTypePerUserEntity.builder()
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
        .dictQuantityTypePerUserEntity(dictQuantityTypePerUserEntity)
        .build();

    dictQuantityTypeRepository.save(dictQuantityTypeEntity);

    log.info("updated dict_QuantityType by new item with id: {}, dict_QuantityType_per_user_id: {} ",
        dictQuantityTypeEntity.getId(),
        dictQuantityTypeEntity.getDictQuantityTypePerUserEntity().getId());
  }

  @Transactional
  public void addDictUnit(NewDictQuantityTypeDto newDictUnitDto) {
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

    return all.stream().map(DictQuantityTypeSerializer::convert)
        .collect(Collectors.toList());
  }
}
