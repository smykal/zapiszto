package com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.service;

import com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.dto.DictBodyParamsDto;
import com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.serializer.DictBodyParamsSerializer;
import com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.repository.DictBodyParamsRepository;
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
}
