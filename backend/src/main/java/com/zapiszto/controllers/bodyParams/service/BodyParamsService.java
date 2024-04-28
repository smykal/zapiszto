package com.zapiszto.controllers.bodyParams.service;

import com.zapiszto.controllers.bodyParams.dto.BodyMassIndexDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.zapiszto.controllers.bodyParams.serializer.BodyParamsSerializer;
import com.zapiszto.controllers.bodyParams.repository.BodyParamsRepository;
import com.zapiszto.controllers.dictBodyParams.repository.DictBodyParamsRepository;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



  public void saveBodyParam(BodyParamsDto bodyParamsDto) {
    var dictBodyParamEntity = dictBodyParamsRepository.getDictBodyParamById(bodyParamsDto.getDict_body_params_id());
    var bodyParamEntity = bodyParamsSerializer.getTestTableEntityFromDto(bodyParamsDto, dictBodyParamEntity);
    bodyParamEntity.setInsert_date(ZonedDateTime.now());
    bodyParamsRepository.save(bodyParamEntity);
  }

  public List<BodyParamsWithNameAndDateDto> getAllBodyParameters(Long userId) {
    var allBodyParams = bodyParamsRepository.getAllById(userId);
    return bodyParamsSerializer.convert(allBodyParams);
  }

  public List<BodyParamsWithNameDto> getActualBodyParametersWithName(Long userId) {
    var actualBodyParamsById = bodyParamsRepository.getActualBodyParamsById(userId);
    return bodyParamsSerializer.convertEntityListToDtoWithParamName(actualBodyParamsById);
  }

  public List<BodyMassIndexDto> getBodyMassIndex(Long userId){
    var bodyHeight = bodyParamsRepository.getLastHeight(userId);
    var bodyParamsEntities = bodyParamsRepository.getWeight(userId);
    return bodyParamsSerializer.convert(bodyParamsEntities, bodyHeight);

  }


}
