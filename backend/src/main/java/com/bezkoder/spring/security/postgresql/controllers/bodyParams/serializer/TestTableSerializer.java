package com.bezkoder.spring.security.postgresql.controllers.testTable.serializer;

import com.bezkoder.spring.security.postgresql.controllers.testTable.dto.BodyParametersDto;
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

  public TestTableEntity getTestTableEntityFromDto(BodyParametersDto bodyParametersDto) {
    return TestTableEntity.builder()
        .kolumna_1(bodyParametersDto.getKolumna_1())
        .kolumna_2(bodyParametersDto.getKolumna_2())
        .userId(bodyParametersDto.getUserId())
        .build();
  }

  public List<BodyParametersDto> convertDtoListoToEntityList(List<TestTableEntity> testTableEntityList) {
    return testTableEntityList.stream()
        .map(testTableEntity -> BodyParametersDto
            .builder()
            .kolumna_1(testTableEntity.getKolumna_1())
            .kolumna_2(testTableEntity.getKolumna_2())
            .userId(testTableEntity.getUserId())
            .build())
        .collect(Collectors.toList());
  }
}
