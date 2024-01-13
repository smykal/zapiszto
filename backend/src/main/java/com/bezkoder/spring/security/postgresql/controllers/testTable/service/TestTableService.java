package com.bezkoder.spring.security.postgresql.controllers.testTable.service;

import com.bezkoder.spring.security.postgresql.controllers.testTable.dto.TestTableDto;
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

  public void saveTestItem(TestTableDto testTableDto) {
    var testTableEntity = testTableSerializer.getTestTableEntityFromDto(testTableDto);
    testTableRepository.save(testTableEntity);
  }

  public List<TestTableDto> getTestTable() {
    var testTableEntityList = testTableRepository.getAll();
    return testTableSerializer.convertDtoListoToEntityList(testTableEntityList);
  }
}
