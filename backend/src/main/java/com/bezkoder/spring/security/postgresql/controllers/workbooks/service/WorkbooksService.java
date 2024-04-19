package com.bezkoder.spring.security.postgresql.controllers.workbooks.service;

import com.bezkoder.spring.security.postgresql.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import com.bezkoder.spring.security.postgresql.controllers.dictWorkbookSchema.repository.DictWorkbookSchemaRepository;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.AddWorkbookDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.PatchWorkbookSchemaDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.WorkbooksDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.entity.WorkbooksEntity;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.repository.WorkbooksRepository;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.serializer.WorkbooksSerializer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class WorkbooksService {

  @Autowired
  WorkbooksRepository workbooksRepository;

  @Autowired
  DictWorkbookSchemaRepository dictWorkbookSchemaRepository;

  public void addWorkbook(
      AddWorkbookDto addWorkbookDto,
      Long userId
  ) {
    final int DEFAULT_DICT_WORKBOOK_SCHEMA = 1;
    DictWorkbookSchemaEntity defaultDictWorkbookSchema = dictWorkbookSchemaRepository.getReferenceById(DEFAULT_DICT_WORKBOOK_SCHEMA);
    int maxOrderNumber = getMaxOrderNumber(userId);
    WorkbooksEntity workbooksEntity = WorkbooksSerializer.convert(
        addWorkbookDto,
        userId,
        maxOrderNumber,
        defaultDictWorkbookSchema
    );
    WorkbooksEntity save = workbooksRepository.save(workbooksEntity);
    int id = save.getId();
    log.info("add new workbook for user {}, with id {}", userId, id);
  }

  private int getMaxOrderNumber(Long userId) {
    try {
      return workbooksRepository.getActualOrderNumber(userId);
    } catch (AopInvocationException e) {
      return 1;
    }

  }

  public List<WorkbooksDto> getWorkbooksForUser(Long userId) {
    List<WorkbooksEntity> allWorkbooks = workbooksRepository.getAllByUserId(userId);
    return allWorkbooks.stream()
        .map(WorkbooksSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteWorkbookById(
      Long id,
      Long userId
  ) {
    workbooksRepository.deleteByIdAndUserId(id, userId);
    log.info("de workbook with id {} for user {}", id, userId);
  }

  @Transactional
  public void updateWorkbookById(PatchWorkbookSchemaDto patchWorkbookSchemaDto) {
    int workbookId = patchWorkbookSchemaDto.getId();
    int dictWorkbookSchemaId = patchWorkbookSchemaDto.getDict_workbook_schema_id();

    var workbook = workbooksRepository.findById(workbookId).get();
    var dictWorkbookSchemaEntity = dictWorkbookSchemaRepository.getReferenceById(dictWorkbookSchemaId);

    workbook.setDictWorkbookSchemaId(dictWorkbookSchemaEntity);
    workbooksRepository.save(workbook);

    log.info("for workbook with id {} updated dict_workbook_schema_id {}", workbookId, dictWorkbookSchemaId);
  }
}
