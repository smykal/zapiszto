package com.bezkoder.spring.security.postgresql.controllers.testTable.service;

import com.bezkoder.spring.security.postgresql.controllers.testTable.dto.BodyParametersDto;
import com.bezkoder.spring.security.postgresql.controllers.testTable.serializer.TestTableSerializer;
import com.bezkoder.spring.security.postgresql.repository.testTable.TestTableRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TestTableService {

  @Autowired
  private TestTableRepository testTableRepository;

  @Autowired
  private TestTableSerializer testTableSerializer;

  public void saveTestItem(BodyParametersDto bodyParametersDto) {
    var testTableEntity = testTableSerializer.getTestTableEntityFromDto(bodyParametersDto);
    testTableRepository.save(testTableEntity);
  }

  public List<BodyParametersDto> getTestTable(Long userId) {
    var testTableEntityList = testTableRepository.getAllById(userId);
    return testTableSerializer.convertDtoListoToEntityList(testTableEntityList);
  }

  public List<BodyParametersDto> getActualBodyParameters(Long userId) {
    var actualBodyParamsById = testTableRepository.getActualBodyParamsById(userId);
    return testTableSerializer.convertDtoListoToEntityList(actualBodyParamsById);
  }
}
