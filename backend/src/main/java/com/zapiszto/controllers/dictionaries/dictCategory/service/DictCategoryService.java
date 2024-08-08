package com.zapiszto.controllers.dictionaries.dictCategory.service;

import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryBasic.entity.DictCategoryBasicEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryBasic.repository.DictCategoryBasicRepository;
import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.repository.DictCategoryPerUserRepository;
import com.zapiszto.controllers.dictionaries.dictCategory.dto.NewDictCategoryDto;
import com.zapiszto.controllers.dictionaries.dictCategory.repository.DictCategoryRepository;
import com.zapiszto.controllers.dictionaries.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictionaries.dictCategory.entity.DictCategoryEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.serializer.DictCategorySerializer;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictCategoryService {

  @Autowired
  DictCategoryRepository dictCategoryRepository;

  @Autowired
  DictCategoryBasicRepository dictCategoryBasicRepository;

  @Autowired
  DictCategoryPerUserRepository dictCategoryPerUserRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Transactional
  public DictCategoryDto addDictCategory(NewDictCategoryDto newDictCategoryDto, Long userId) {
    Languages lang = userDetailsRepository.userLanguage(userId);
    var item = DictCategoryPerUserEntity.builder()
        .name(newDictCategoryDto.name())
        .user_id(userId)
        .description(newDictCategoryDto.description())
        .build();

    DictCategoryPerUserEntity dictCategoryPerUserEntity = dictCategoryPerUserRepository.save(item);

    log.info(
        "add new item to dict_Category_per_user: id {}, value {}, user {}",
        dictCategoryPerUserEntity.getId(),
        newDictCategoryDto.name(),
        userId
    );

    DictCategoryEntity dictCategoryEntity = DictCategoryEntity.builder()
        .dictCategoryPerUserEntity(dictCategoryPerUserEntity)
        .build();

    DictCategoryEntity entity = dictCategoryRepository.save(dictCategoryEntity);


    log.info(
        "updated dict_Category by new item with id: {}, dict_Category_per_user_id: {} ",
        dictCategoryEntity.getId(),
        dictCategoryEntity.getDictCategoryPerUserEntity()
            .getId()
    );

    return DictCategorySerializer.convert(entity, lang);
  }

  @Transactional
  public void addDictCategoryBasic(NewDictCategoryDto newDictCategoryDto, Long userId) {
    Languages lang = userDetailsRepository.userLanguage(userId);
    var item = DictCategoryBasicEntity.builder()
        .name(newDictCategoryDto.name())
        .description(newDictCategoryDto.description())
        .build();

    DictCategoryBasicEntity dictCategoryBasicEntity =
        dictCategoryBasicRepository.save(item);

    log.info(
        "add new item to dict_Category_basic: id {}, value {}",
        dictCategoryBasicEntity.getId(),
        newDictCategoryDto.name()
    );

    DictCategoryEntity dictCategoryEntity = DictCategoryEntity.builder()
        .dictCategoryBasicEntity(dictCategoryBasicEntity)
        .build();

    dictCategoryRepository.save(dictCategoryEntity);

    log.info(
        "updated dict_Category by new item with id: {}, dict_Category_basic_id: {} ",
        dictCategoryEntity.getId(),
        dictCategoryEntity.getDictCategoryBasicEntity()
            .getId()
    );
  }

  public List<DictCategoryDto> getDictCategory(Long userId) {
    List<DictCategoryEntity> all = dictCategoryRepository.getAllForUser(userId);
    Languages lang = userDetailsRepository.userLanguage(userId);

    return all.stream()
        .map(dictCategoryEntity -> DictCategorySerializer.convert(dictCategoryEntity, lang))
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictCategoryPerUser(Long userId, int itemToDelete) {
    try {
      dictCategoryRepository.deleteDictCategoryPerUser(itemToDelete);
      dictCategoryRepository.deleteDictCategoryPerUser(itemToDelete, userId);
      log.info("deleted dict_Category_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictCategoryBasic(Long userId, int itemToDelete) {
    try {
      dictCategoryRepository.deleteDictCategory(itemToDelete);
      dictCategoryRepository.deleteDictCategoryBasic(itemToDelete);
      log.info("deleted dict_Category_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }
}
  
