package com.zapiszto.controllers.bodyParams.service;

import com.zapiszto.controllers.bodyParams.dto.BodyMassIndexDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.zapiszto.controllers.bodyParams.serializer.BodyParamsSerializer;
import com.zapiszto.controllers.bodyParams.repository.BodyParamsRepository;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.clients.repository.ClientsRepository;
import com.zapiszto.controllers.dictionaries.dictBodyParams.repository.DictBodyParamsRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BodyParamsService {

  @Autowired
  private BodyParamsRepository bodyParamsRepository;

  @Autowired
  private BodyParamsSerializer bodyParamsSerializer;

  @Autowired
  private DictBodyParamsRepository dictBodyParamsRepository;

  @Autowired
  private ClientsRepository clientsRepository;

  public void saveBodyParam(BodyParamsDto bodyParamsDto, Long userId) {
    var dictBodyParamEntity = dictBodyParamsRepository.getDictBodyParamById(bodyParamsDto.dict_body_params_id());
    if(bodyParamsDto.clientId() != null) {
      var userIdByClientId = clientsRepository.getByIdUuid(bodyParamsDto.clientId());
      userId = userIdByClientId.getUserId();
    }
    var bodyParamEntity = bodyParamsSerializer.getTestTableEntityFromDto(bodyParamsDto, dictBodyParamEntity, userId);
    bodyParamEntity.setInsert_date(ZonedDateTime.now());
    bodyParamsRepository.save(bodyParamEntity);
  }

  public List<BodyParamsWithNameAndDateDto> getAllBodyParameters(Long userId) {
    var allBodyParams = bodyParamsRepository.getAllById(userId);
    return bodyParamsSerializer.convert(allBodyParams);
  }
  public List<BodyParamsWithNameAndDateDto> getAllBodyParameters(Long userId, Long trainerId) throws Exception {
    if (!checkIfUserBelongsToTrainer(userId, trainerId)) {
      throw new Exception("User does not belong to the trainer.");
    }
    var allBodyParams = bodyParamsRepository.getAllById(userId);
    return bodyParamsSerializer.convert(allBodyParams);
  }

  public List<BodyParamsWithNameDto> getActualBodyParametersWithName(Long userId) {
    var actualBodyParamsById = bodyParamsRepository.getActualBodyParamsById(userId);
    return bodyParamsSerializer.convertEntityListToDtoWithParamName(actualBodyParamsById);
  }

  public List<BodyParamsWithNameDto> getActualBodyParametersWithName(Long userId, Long trainerId) throws Exception {
    if (!checkIfUserBelongsToTrainer(userId, trainerId)) {
      throw new Exception("User does not belong to the trainer.");
    }
    var actualBodyParamsById = bodyParamsRepository.getActualBodyParamsById(userId);
    return bodyParamsSerializer.convertEntityListToDtoWithParamName(actualBodyParamsById);
  }

  public List<BodyMassIndexDto> getBodyMassIndex(Long userId) {
    try {
      var bodyHeight = bodyParamsRepository.getLastHeight(userId);
      var bodyParamsEntities = bodyParamsRepository.getWeight(userId);
      return bodyParamsSerializer.convert(bodyParamsEntities, bodyHeight);
    } catch (AopInvocationException e) {
      throw new RuntimeException("Error retrieving body mass index", e);
    }
  }

  public boolean checkIfUserBelongsToTrainer(Long userId, Long trainerId){
    List<ClientEntity> allByTrainerId = clientsRepository.getAllByTrainerId(trainerId);
    return allByTrainerId.stream()
        .anyMatch(clientEntity -> Objects.equals(clientEntity.getUserId(), userId));
  }


}
