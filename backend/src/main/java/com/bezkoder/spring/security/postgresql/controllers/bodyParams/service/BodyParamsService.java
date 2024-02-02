package com.bezkoder.spring.security.postgresql.controllers.bodyParams.service;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.serializer.BodyParamsSerializer;
import com.bezkoder.spring.security.postgresql.repository.bodyParams.TestTableRepository;
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
  private TestTableRepository testTableRepository;

  @Autowired
  private BodyParamsSerializer bodyParamsSerializer;

  @Autowired
  private DictBodyParamsRepository dictBodyParamsRepository;

  public void saveTestItem(BodyParamsDto bodyParamsDto) {
    var dictBodyParamEntity = dictBodyParamsRepository.getDictBodyParamById(bodyParamsDto.getDict_body_params_id());
    var testTableEntity = bodyParamsSerializer.getTestTableEntityFromDto(bodyParamsDto, dictBodyParamEntity);
    testTableRepository.save(testTableEntity);
  }

  public List<BodyParamsDto> getTestTable(Long userId) {
    var testTableEntityList = testTableRepository.getAllById(userId);
    return bodyParamsSerializer.convertDtoListoToEntityList(testTableEntityList);
  }

  public List<BodyParamsDto> getActualBodyParameters(Long userId) {
    var actualBodyParamsById = testTableRepository.getActualBodyParamsById(userId);
    return bodyParamsSerializer.convertDtoListoToEntityList(actualBodyParamsById);
  }

  public List<BodyParamsWithNameDto> getActualBodyParametersWithName(Long userId) {
    var actualBodyParamsById = testTableRepository.getActualBodyParamsById(userId);
    return bodyParamsSerializer.convertEntityListToDtoWithParamName(actualBodyParamsById);
  }
}
