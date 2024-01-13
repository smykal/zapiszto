package com.bezkoder.spring.security.postgresql.controllers.testTable.serializer;

import com.bezkoder.spring.security.postgresql.controllers.testTable.dto.TestTableDto;
import com.bezkoder.spring.security.postgresql.controllers.testTable.entity.TestTableEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TestTableSerializer {

  public TestTableEntity getTestTableEntityFromDto(TestTableDto testTableDto) {
    return TestTableEntity.builder()
        .kolumna_1(testTableDto.getKolumna_1())
        .kolumna_2(testTableDto.getKolumna_2())
        .build();
  }

  public List<TestTableDto> convertDtoListoToEntityList(List<TestTableEntity> testTableEntityList) {
    return testTableEntityList.stream()
        .map(testTableEntity -> TestTableDto
            .builder()
            .kolumna_1(testTableEntity.getKolumna_1())
            .kolumna_2(testTableEntity.getKolumna_2())
            .build())
        .collect(Collectors.toList());
  }
}
