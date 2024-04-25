package com.zapiszto.controllers.dictWorkbookSchema.service;

import com.zapiszto.controllers.dictWorkbookSchema.dto.DictWorkbookSchemaDto;
import com.zapiszto.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import com.zapiszto.controllers.dictWorkbookSchema.repository.DictWorkbookSchemaRepository;
import com.zapiszto.controllers.dictWorkbookSchema.serializer.DictWorkbookSchemaSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DictWorkbookSchemaService {

  @Autowired
  DictWorkbookSchemaRepository dictWorkbookSchemaRepository;

  public List<DictWorkbookSchemaDto> getAll(){
    List<DictWorkbookSchemaEntity> all = dictWorkbookSchemaRepository.findAll();

    return all.stream()
        .map(DictWorkbookSchemaSerializer::convert)
        .collect(Collectors.toList());
  }
}
