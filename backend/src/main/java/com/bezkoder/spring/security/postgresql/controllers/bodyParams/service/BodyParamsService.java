package com.bezkoder.spring.security.postgresql.controllers.bodyParams.service;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.serializer.BodyParamsSerializer;
import com.bezkoder.spring.security.postgresql.repository.bodyParams.BodyParamsRepository;
import com.bezkoder.spring.security.postgresql.repository.dictBodyParams.DictBodyParamsRepository;
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
}
