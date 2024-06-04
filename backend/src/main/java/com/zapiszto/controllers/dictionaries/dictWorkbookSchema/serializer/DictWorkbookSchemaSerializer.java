package com.zapiszto.controllers.dictWorkbookSchema.serializer;

import com.zapiszto.controllers.dictWorkbookSchema.dto.DictWorkbookSchemaDto;
import com.zapiszto.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import org.springframework.stereotype.Component;

@Component
public class DictWorkbookSchemaSerializer {

  public static DictWorkbookSchemaDto convert(DictWorkbookSchemaEntity dictWorkbookSchemaEntity) {
    return DictWorkbookSchemaDto.builder()
        .id(dictWorkbookSchemaEntity.getId())
        .name(dictWorkbookSchemaEntity.getName())
        .description(dictWorkbookSchemaEntity.getDescription())
        .build();
  }
}
