package com.zapiszto.controllers.program.clientProgramView.service;

import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.clients.repository.ClientsRepository;
import com.zapiszto.controllers.dictionaries.dictEquipment.repository.DictEquipmentRepository;
import com.zapiszto.controllers.dictionaries.dictExercises.repository.DictExercisesRepository;
import com.zapiszto.controllers.dictionaries.dictQuantityType.repository.DictQuantityTypeRepository;
import com.zapiszto.controllers.dictionaries.dictSessionPart.repository.DictSessionPartRepository;
import com.zapiszto.controllers.dictionaries.dictUnits.repository.DictUnitsRepository;
import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseSessionRepository;
import com.zapiszto.controllers.exercises.serializer.ExerciseSerializer;
import com.zapiszto.controllers.program.clientProgramView.dto.*;
import com.zapiszto.controllers.program.clientProgramView.repository.ClientProgramRepository;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientProgramService {

  @Autowired
  ClientProgramRepository clientProgramRepository;

  @Autowired
  DictExercisesRepository dictExercisesRepository;

  @Autowired
  DictQuantityTypeRepository dictQuantityTypeRepository;

  @Autowired
  DictUnitsRepository dictUnitsRepository;

  @Autowired
  DictSessionPartRepository dictSessionPartRepository;

  @Autowired
  DictEquipmentRepository dictEquipmentRepository;

  @Autowired
  ClientsRepository clientsRepository;


  public List<ClientProgramDto> getClientPrograms(UUID clientId) {
      ClientEntity byIdUuid = clientsRepository.getByIdUuid(clientId);
      var trainerId = byIdUuid.getTrainerId();
    List<MacrocycleEntity> macrocycles = clientProgramRepository.getAllForClientOrUser(clientId, trainerId);

    // Convert MacrocycleEntities to ClientProgramDtos
    return macrocycles.stream().map(x -> convertToClientProgramDto(x, trainerId)).collect(Collectors.toList());
  }

  private ClientProgramDto convertToClientProgramDto(MacrocycleEntity macrocycleEntity, Long trainerId) {
    List<ClientProgramMacrocycleDto> macrocycles = List.of(new ClientProgramMacrocycleDto(
        macrocycleEntity.getId(),
        macrocycleEntity.getMesocycles().stream().map(x -> convertToClientProgramMesocycleDto(x, trainerId)).collect(Collectors.toList())
    ));

    return new ClientProgramDto(macrocycleEntity.getProgramEntity().getName(), macrocycles);
  }

  private ClientProgramMesocycleDto convertToClientProgramMesocycleDto(MesocycleEntity mesocycleEntity, Long trainerId) {
    return new ClientProgramMesocycleDto(
        mesocycleEntity.getOrderId(),
        mesocycleEntity.getMicrocycles().stream()
            .filter(MicrocycleEntity::isShare)
            .map(x -> convertToClientProgramMicrocycleDto(x, trainerId)).collect(Collectors.toList())
    );
  }

  private ClientProgramMicrocycleDto convertToClientProgramMicrocycleDto(MicrocycleEntity microcycleEntity, Long trainerId) {
    return new ClientProgramMicrocycleDto(
        microcycleEntity.getOrderId(),
        microcycleEntity.getSessions().stream().map(x -> convertToClientProgramSessionDto(x, trainerId)).collect(Collectors.toList())
    );
  }

  private ClientProgramSessionDto convertToClientProgramSessionDto(SessionEntity sessionEntity, Long trainerId) {
    return new ClientProgramSessionDto(
        sessionEntity.getOrderId(),
        sessionEntity.getExercises().stream().map(ex -> convertToClientProgramExerciseDto(ex, trainerId)).collect(Collectors.toList())
    );
  }

  private ClientProgramExerciseDto convertToClientProgramExerciseDto(ExerciseEntity exerciseEntity, Long trainerId) {
    var dictExercises = dictExercisesRepository.getAllForUser(trainerId);
    var dictQuantityType = dictQuantityTypeRepository.getAllForUser(trainerId);
    var dictUnits = dictUnitsRepository.getAllForUser(trainerId);
    var dictSessionParts = dictSessionPartRepository.getAllForUser(trainerId);
    var dictEquipment = dictEquipmentRepository.getAllForUser(trainerId);

    var dictCategoryName = ExerciseSerializer.getDictCategoryName(dictExercises, exerciseEntity.getDictExerciseId());
    var exerciseName = ExerciseSerializer.getExerciseName(dictExercises, exerciseEntity.getDictExerciseId());
    var repetitionsUnit = ExerciseSerializer.getQuantityTypeName(dictQuantityType, exerciseEntity.getDictQuantityTypeId());
    var weightUnit = ExerciseSerializer.getUnitName(dictUnits, exerciseEntity.getDictUnitId());
    var purpose = ExerciseSerializer.getSessionPartName(dictSessionParts, exerciseEntity.getDictSessionPartId());

    return new ClientProgramExerciseDto(
        exerciseEntity.getOrderNumber(),
        purpose,
        dictCategoryName,
        exerciseName,
        exerciseEntity.getVolume(),
        weightUnit,
        exerciseEntity.getQuantity(),
        repetitionsUnit
    );
  }
}
