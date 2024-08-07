package com.zapiszto.controllers.dictionaries.dictExercises.service;

import com.zapiszto.controllers.dictionaries.dictCategory.entity.DictCategoryEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.repository.DictCategoryRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.repository.DictExercisesBasicRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesPerUser.repository.DictExercisesPerUserRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.dto.DictExercisesDto;
import com.zapiszto.controllers.dictionaries.dictExercises.dto.NewDictExerciseDto;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.serializer.DictExercisesSerializer;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DictExerciseService {

  @Autowired
  DictExercisesPerUserRepository dictExercisesPerUserRepository;

  @Autowired
  DictExercisesBasicRepository dictExercisesBasicRepository;

  @Autowired
  DictExercisesRepository dictExercisesRepository;

  @Autowired
  DictCategoryRepository dictCategoryRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Transactional
  public DictExercisesDto addDictExercise(NewDictExerciseDto newDictExerciseDto, Long userId) {
    Languages lang = userDetailsRepository.userLanguage(userId);
    var  item = DictExercisesPerUserEntity.builder()
        .id(newDictExerciseDto.id())
        .name(newDictExerciseDto.name())
        .user_id(userId)
        .dictCategoryId(newDictExerciseDto.categoryId())
        .build();

    DictExercisesPerUserEntity dictExercisesPerUserEntity = dictExercisesPerUserRepository.save(item);

    log.info(
        "add new item to dict_exercises_per_user: id {}, value {}, user {}",
        dictExercisesPerUserEntity.getId(),
        newDictExerciseDto.name(),
        userId
    );


    DictExercisesEntity dictExercisesEntity = DictExercisesEntity.builder()
        .dictExercisesPerUserEntity(dictExercisesPerUserEntity)
        .build();

    DictExercisesEntity entity = dictExercisesRepository.save(dictExercisesEntity);


    DictCategoryEntity dictCategoryEntity = dictCategoryRepository.getReferenceById(newDictExerciseDto.categoryId()
        .intValue());
    dictExercisesPerUserEntity.setDictCategoryEntity(dictCategoryEntity);
    entity.setDictExercisesPerUserEntity(dictExercisesPerUserEntity);

    log.info(
        "updated dict_exercises by new item with id: {}, dict_exercises_per_user_id: {} ",
        dictExercisesEntity.getId(),
        dictExercisesEntity.getDictExercisesPerUserEntity()
            .getId()
    );

    return DictExercisesSerializer.convert(entity, lang);
  }

  @Transactional
  public void addDictExercise(NewDictExerciseDto newDictExerciseDto) {
    var item = DictExercisesBasicEntity.builder()
            .id(newDictExerciseDto.id())
            .name(newDictExerciseDto.name())
            .build();

    DictExercisesBasicEntity dictExercisesBasicEntity =
        dictExercisesBasicRepository.save(item);

    log.info(
        "add new item to dict_exercises_basic: id {}, value {}",
        dictExercisesBasicEntity.getId(),
        newDictExerciseDto.name()
    );

    DictExercisesEntity dictExercisesEntity = DictExercisesEntity.builder()
        .dictExercisesBasicEntity(dictExercisesBasicEntity)
        .build();

    dictExercisesRepository.save(dictExercisesEntity);

    log.info(
        "updated dict_exercises by new item with id: {}, dict_exercises_basic_id: {} ",
        dictExercisesEntity.getId(),
        dictExercisesEntity.getDictExercisesBasicEntity()
            .getId()
    );
  }

  public List<DictExercisesDto> getDictExercises(Long userId) {
    List<DictExercisesEntity> all = dictExercisesRepository.getAllForUser(userId);
    Languages lang = userDetailsRepository.userLanguage(userId);

    return all.stream()
        .map(dictExercisesEntity -> DictExercisesSerializer.convert(dictExercisesEntity, lang))
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictExercisePerUser(Long userId, UUID itemToDelete) {
    try {
      dictExercisesRepository.deleteDictExercisePerUser(itemToDelete);
      dictExercisesRepository.deleteDictExercisePerUser(itemToDelete, userId);
      log.info("deleted dict_exercises_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictExerciseBasic(Long userId, UUID itemToDelete) {
    try {
      dictExercisesRepository.deleteDictExercise(itemToDelete);
      dictExercisesRepository.deleteDictExerciseBasic(itemToDelete);
      log.info("deleted dict_exercises_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    } catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }
}
