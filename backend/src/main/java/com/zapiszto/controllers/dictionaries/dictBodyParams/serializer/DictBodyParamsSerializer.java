package com.zapiszto.controllers.dictionaries.dictBodyParams.serializer;

import com.zapiszto.controllers.dictionaries.dictBodyParams.dto.DictBodyParamsDto;
import com.zapiszto.controllers.dictionaries.dictBodyParams.dto.NewDictBodyParamDto;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DictBodyParamsSerializer {

  public List<DictBodyParamsDto> convertEntityToDto(List<DictBodyParamsEntity> dictBodyParamsEntityList) {
    return dictBodyParamsEntityList.stream()
        .map(dictBodyParamsEntity -> DictBodyParamsDto
            .builder()
            .id(dictBodyParamsEntity.getId())
            .name(dictBodyParamsEntity.getName())
            .description(dictBodyParamsEntity.getDescription())
            .build())
        .collect(Collectors.toList());
  }

  public DictBodyParamsEntity convert (NewDictBodyParamDto dictBodyParamDto) {
    return DictBodyParamsEntity.builder()
        .name(dictBodyParamDto.getName())
        .description(dictBodyParamDto.getDescription())
        .build();
  }
}
