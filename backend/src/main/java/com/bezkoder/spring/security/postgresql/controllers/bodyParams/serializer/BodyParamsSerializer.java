package com.bezkoder.spring.security.postgresql.controllers.bodyParams.serializer;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.entity.BodyParamsEntity;
import com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.entity.DictBodyParamsEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BodyParamsSerializer {

  public BodyParamsEntity getTestTableEntityFromDto(BodyParamsDto bodyParamsDto, DictBodyParamsEntity dictBodyParamsEntity) {


    return BodyParamsEntity.builder()
        .dictBodyParams(dictBodyParamsEntity)
        .value(bodyParamsDto.getValue())
        .userId(bodyParamsDto.getUserId())
        .build();
  }

  public List<BodyParamsDto> convertDtoListoToEntityList(List<BodyParamsEntity> bodyParamsEntityList) {
    return bodyParamsEntityList.stream()
        .map(bodyParamsEntity -> BodyParamsDto
            .builder()
            .dict_body_params_id(bodyParamsEntity.getDictBodyParams().getId())
            .value(bodyParamsEntity.getValue())
            .userId(bodyParamsEntity.getUserId())
            .build())
        .collect(Collectors.toList());
  }

  public List<BodyParamsWithNameDto> convertEntityListToDtoWithParamName(List<BodyParamsEntity> bodyParamsEntityList) {
    return bodyParamsEntityList.stream()
        .map(bodyParamsEntity -> BodyParamsWithNameDto
            .builder()
            .dict_body_params_name(bodyParamsEntity.getDictBodyParams().getName())
            .value(bodyParamsEntity.getValue())
            .userId(bodyParamsEntity.getUserId())
            .build())
        .collect(Collectors.toList());
  }

}
