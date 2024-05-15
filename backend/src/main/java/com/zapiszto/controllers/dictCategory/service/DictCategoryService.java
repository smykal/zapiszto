package com.zapiszto.controllers.dictCategory.service;

import com.zapiszto.controllers.dictCategory.dictCategoryBasic.entity.DictCategoryBasicEntity;
import com.zapiszto.controllers.dictCategory.dictCategoryBasic.repository.DictCategoryBasicRepository;
import com.zapiszto.controllers.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import com.zapiszto.controllers.dictCategory.dictCategoryPerUser.repository.DictCategoryPerUserRepository;
import com.zapiszto.controllers.dictCategory.dto.NewDictCategoryDto;
import com.zapiszto.controllers.dictCategory.repository.DictCategoryRepository;
import com.zapiszto.controllers.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictCategory.entity.DictCategoryEntity;
import com.zapiszto.controllers.dictCategory.serializer.DictCategorySerializer;
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

  @Transactional
  public DictCategoryDto addDictCategory(NewDictCategoryDto newDictCategoryDto, Long userId) {
    var item = DictCategoryPerUserEntity.builder()
        .name(newDictCategoryDto.getName())
        .user_id(userId)
        .build();

    DictCategoryPerUserEntity dictCategoryPerUserEntity = dictCategoryPerUserRepository.save(item);

    log.info(
        "add new item to dict_Category_per_user: id {}, value {}, user {}",
        dictCategoryPerUserEntity.getId(),
        newDictCategoryDto.getName(),
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

    return DictCategorySerializer.convert(entity);
  }

  @Transactional
  public void addDictCategory(NewDictCategoryDto newDictCategoryDto) {
    var item = DictCategoryBasicEntity.builder()
            .name(newDictCategoryDto.getName())
        .build();

    DictCategoryBasicEntity dictCategoryBasicEntity =
        dictCategoryBasicRepository.save(item);

    log.info(
        "add new item to dict_Category_basic: id {}, value {}",
        dictCategoryBasicEntity.getId(),
        newDictCategoryDto.getName()
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

    return all.stream()
        .map(DictCategorySerializer::convert)
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
  
