package com.zapiszto.controllers.dictExercises.service;

import com.zapiszto.controllers.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import com.zapiszto.controllers.dictExercises.dictExercisesBasic.repository.DictExercisesBasicRepository;
import com.zapiszto.controllers.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import com.zapiszto.controllers.dictExercises.dictExercisesPerUser.repository.DictExercisesPerUserRepository;
import com.zapiszto.controllers.dictExercises.dto.DictExercisesDto;
import com.zapiszto.controllers.dictExercises.dto.NewDictExerciseDto;
import com.zapiszto.controllers.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictExercises.serializer.DictExercisesSerializer;
import java.util.List;
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

  @Transactional
  public DictExercisesDto addDictExercise(NewDictExerciseDto newDictExerciseDto, Long userId) {
    var item = DictExercisesPerUserEntity.builder()
            .name(newDictExerciseDto.getName())
            .user_id(userId)
            .build();

    DictExercisesPerUserEntity dictExercisesPerUserEntity = dictExercisesPerUserRepository.save(item);

    log.info("add new item to dict_exercises_per_user: id {}, value {}, user {}",
        dictExercisesPerUserEntity.getId(),
        newDictExerciseDto.getName(),
        userId);

    DictExercisesEntity dictExercisesEntity = DictExercisesEntity.builder()
        .dictExercisesPerUserEntity(dictExercisesPerUserEntity)
        .build();

    DictExercisesEntity entity = dictExercisesRepository.save(dictExercisesEntity);



    log.info("updated dict_exercises by new item with id: {}, dict_exercises_per_user_id: {} ",
        dictExercisesEntity.getId(),
        dictExercisesEntity.getDictExercisesPerUserEntity().getId());

    return DictExercisesSerializer.convert(entity);
  }

  @Transactional
  public void addDictExercise(NewDictExerciseDto newDictExerciseDto) {
    var item = DictExercisesBasicEntity.builder()
            .name(newDictExerciseDto.getName())
        .build();

    DictExercisesBasicEntity dictExercisesBasicEntity =
        dictExercisesBasicRepository.save(item);

    log.info("add new item to dict_exercises_basic: id {}, value {}",
        dictExercisesBasicEntity.getId(),
        newDictExerciseDto.getName());

    DictExercisesEntity dictExercisesEntity = DictExercisesEntity.builder()
        .dictExercisesBasicEntity(dictExercisesBasicEntity)
        .build();

    dictExercisesRepository.save(dictExercisesEntity);

    log.info("updated dict_exercises by new item with id: {}, dict_exercises_basic_id: {} ",
        dictExercisesEntity.getId(),
        dictExercisesEntity.getDictExercisesBasicEntity().getId());
  }

  public List<DictExercisesDto> getDictExercises(Long userId){
    List<DictExercisesEntity> all = dictExercisesRepository.getAllForUser(userId);

    return all.stream().map(DictExercisesSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public String deleteDictExercisePerUser(Long userId, int itemToDelete) {
    try {
      dictExercisesRepository.deleteDictExercisePerUser(itemToDelete);
      dictExercisesRepository.deleteDictExercisePerUser(itemToDelete, userId);
      log.info("deleted dict_exercises_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }

  @Transactional
  public String deleteDictExerciseBasic(Long userId, int itemToDelete) {
    try {
      dictExercisesRepository.deleteDictExercise(itemToDelete);
      dictExercisesRepository.deleteDictExerciseBasic(itemToDelete);
      log.info("deleted dict_exercises_per_user with id: {}, user: {}", itemToDelete, userId);
      return "ok";
    }  catch (DataIntegrityViolationException e) {
      log.error("Error deleting exercise: {}", e.getMessage());
      throw new RuntimeException("Cannot delete exercise due to data integrity violation");
    }
  }
}
