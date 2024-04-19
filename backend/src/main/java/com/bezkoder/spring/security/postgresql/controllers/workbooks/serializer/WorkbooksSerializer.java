package com.bezkoder.spring.security.postgresql.controllers.workbooks.serializer;

import com.bezkoder.spring.security.postgresql.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.AddWorkbookDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.WorkbooksDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.entity.WorkbooksEntity;
import java.time.ZonedDateTime;

public class WorkbooksSerializer {

  public static WorkbooksEntity convert(
      AddWorkbookDto addWorkbookDto,
      Long userId,
      int maxOrderNumber,
      DictWorkbookSchemaEntity dictWorkbookSchemaEntity
  ) {


    return WorkbooksEntity.builder()
        .name(addWorkbookDto.getName())
        .orderNumber(maxOrderNumber)
        .userId(userId)
        .insert_date(ZonedDateTime.now())
        .dictWorkbookSchemaId(dictWorkbookSchemaEntity)
        .build();
  }

  public static WorkbooksDto convert(WorkbooksEntity workbooksEntity) {
    return WorkbooksDto.builder()
        .id(workbooksEntity.getId())
        .name(workbooksEntity.getName())
        .order_number(workbooksEntity.getOrderNumber())
        .insert_date(workbooksEntity.getInsert_date().toString())
        .dict_workbook_schema_name(workbooksEntity.getDictWorkbookSchemaId().getName())
        .build();
  }
}