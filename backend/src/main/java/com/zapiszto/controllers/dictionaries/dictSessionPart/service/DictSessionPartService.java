package com.zapiszto.controllers.dictionaries.dictSessionPart.service;

import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartBasic.repository.DictSessionPartBasicRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartPerUser.entity.DictSessionPartPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartPerUser.repository.DictSessionPartPerUserRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.DictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dto.NewDictSessionPartDto;
import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.repository.DictSessionPartRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartBasic.entity.DictSessionPartBasicEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.serializer.DictSessionPartSerializer;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DictSessionPartService {

  @Autowired
  DictSessionPartRepository dictSessionPartRepository;

  @Autowired
  DictSessionPartBasicRepository dictSessionPartBasicRepository;

  @Autowired
  DictSessionPartPerUserRepository dictSessionPartPerUserRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  public void addDictSessionPart(NewDictSessionPartDto newDictSessionPartDto, Long userId) {
    var item = DictSessionPartPerUserEntity.builder()
        .name(newDictSessionPartDto.name())
        .user_id(userId)
        .build();

    DictSessionPartPerUserEntity dictSessionPartPerUserEntity = dictSessionPartPerUserRepository.save(item);

    log.info(
        "add new item to dict_units_per_user: id {}, value {}, user {}",
        dictSessionPartPerUserEntity.getId(),
        newDictSessionPartDto.name(),
        userId
    );

    DictSessionPartEntity dictSessionPartEntity = DictSessionPartEntity.builder()
        .dictSessionPartPerUserEntity(dictSessionPartPerUserEntity)
        .build();

    dictSessionPartRepository.save(dictSessionPartEntity);

    log.info(
        "updated dict_units by new item with id: {}, dict_units_per_user_id: {} ",
        dictSessionPartEntity.getId(),
        dictSessionPartEntity.getDictSessionPartPerUserEntity()
            .getId()
    );
  }

  public void addDictSessionPart(NewDictSessionPartDto newDictSessionPartDto) {
    var item = DictSessionPartBasicEntity.builder()
        .name(newDictSessionPartDto.name())
        .build();

    DictSessionPartBasicEntity dictSessionPartBasicEntity = dictSessionPartBasicRepository.save(item);

    log.info(
        "add new item to dict_units_basic: id {}, value {}, shortcut {}",
        dictSessionPartBasicEntity.getId(),
        newDictSessionPartDto.name()
    );

    DictSessionPartEntity dictSessionPartEntity = DictSessionPartEntity.builder()
        .dictSessionPartBasicEntity(dictSessionPartBasicEntity)
        .build();

    dictSessionPartRepository.save(dictSessionPartEntity);

    log.info(
        "updated dict_units by new item with id: {}, dict_units_basic_id: {} ",
        dictSessionPartEntity.getId(),
        dictSessionPartEntity.getDictSessionPartBasicEntity()
            .getId()
    );
  }

  public List<DictSessionPartDto> getDictSessionPart(Long userId) {
    List<DictSessionPartEntity> all = dictSessionPartRepository.getAllForUser(userId);
    Languages lang = userDetailsRepository.userLanguage(userId);

    return all.stream()
        .map(dictSessionPartEntity -> DictSessionPartSerializer.convert(dictSessionPartEntity, lang))
        .collect(Collectors.toList());

  }

  public String deleteDictSessionPartPerUser(Long userId, int itemToDelete) {
    try {
      dictSessionPartRepository.deleteDictSessionPartPerUser(itemToDelete);
      dictSessionPartRepository.deleteDictSessionPartPerUser(itemToDelete, userId);
      log.info("deleted dict_unit_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict unit: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict unit due to data integrity violation");
    }

  }

  public String deleteDictSessionPartBasic(Long userId, int itemToDelete) {
    try {
      dictSessionPartRepository.deleteDictSessionPart(itemToDelete);
      dictSessionPartRepository.deleteDictSessionPartBasic(itemToDelete);
      log.info("deleted dict_unit_basic with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting dict unit: {}", e.getMessage());
      throw new RuntimeException("Cannot delete dict unit due to data integrity violation");
    }
  }
}
