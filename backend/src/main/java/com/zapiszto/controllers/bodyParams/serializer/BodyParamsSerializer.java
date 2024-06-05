package com.zapiszto.controllers.bodyParams.serializer;

import com.zapiszto.controllers.bodyParams.dto.BmiCategory;
import com.zapiszto.controllers.bodyParams.dto.BodyMassIndexDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameAndDateDto;
import com.zapiszto.controllers.bodyParams.dto.BodyParamsWithNameDto;
import com.zapiszto.controllers.bodyParams.entity.BodyParamsEntity;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import java.time.format.DateTimeFormatter;
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

  public List<BodyParamsWithNameAndDateDto> convert(List<BodyParamsEntity> bodyParamsEntityList) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    return bodyParamsEntityList.stream()
        .map(bodyParamsEntity -> BodyParamsWithNameAndDateDto
            .builder()
            .dict_body_params_name(bodyParamsEntity.getDictBodyParams().getName())
            .value(Integer.parseInt(bodyParamsEntity.getValue()))
            .insert_date(bodyParamsEntity.getInsert_date())
            .build())
        .collect(Collectors.toList());  }

  public List<BodyMassIndexDto> convert(List<BodyParamsEntity> bodyParamsEntities, int height){
    return bodyParamsEntities.stream()
        .map(bodyParamsEntity -> BodyMassIndexDto.builder()
            .date(bodyParamsEntity.getInsert_date())
            .value(calculateBmi(bodyParamsEntity.getValue(), height))
            .description(getBmiDescription(bodyParamsEntity.getValue(), height))
            .build())
        .collect(Collectors.toList());
  }

  private double calculateBmi(String value, int height) {
    double weight = Double.parseDouble(value);
    double heightInMeters = height / 100.0;
    return weight / (heightInMeters * heightInMeters);
  }

  private String getBmiDescription(String value, int height) {
    var bmi = calculateBmi(value, height);
    for (BmiCategory category : BmiCategory.values()) {
      if (bmi >= category.getRange()[0] && bmi < category.getRange()[1]) {
        return category.getDescriptionPL();
      }
    }
    return "Nieznany";
  }
}
