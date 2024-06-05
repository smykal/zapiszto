package com.zapiszto.controllers.dictionaries.dictBodyParams.service;

import com.zapiszto.controllers.dictionaries.dictBodyParams.dto.DictBodyParamsDto;
import com.zapiszto.controllers.dictionaries.dictBodyParams.dto.NewDictBodyParamDto;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import com.zapiszto.controllers.dictionaries.dictBodyParams.serializer.DictBodyParamsSerializer;
import com.zapiszto.controllers.dictionaries.dictBodyParams.repository.DictBodyParamsRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DictBodyParamsService {

  @Autowired
  private DictBodyParamsRepository dictBodyParamsRepository;

  @Autowired
  private DictBodyParamsSerializer dictBodyParamsSerializer;

  public List<DictBodyParamsDto> getDictBodyParams() {
    var dictBodyParamsEntityList = dictBodyParamsRepository.findAll();
    return dictBodyParamsSerializer.convertEntityToDto(dictBodyParamsEntityList);
  }

  public void saveDictBodyParams(NewDictBodyParamDto dictBodyParamDto) {
    DictBodyParamsEntity dictBodyParamsEntity = dictBodyParamsSerializer.convert(dictBodyParamDto);
    dictBodyParamsRepository.save(dictBodyParamsEntity);
  }
}
