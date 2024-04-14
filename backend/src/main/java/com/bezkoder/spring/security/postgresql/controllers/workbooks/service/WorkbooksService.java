package com.bezkoder.spring.security.postgresql.controllers.workbooks.service;

import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.AddWorkbookDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.WorkbooksDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.entity.WorkbooksEntity;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.repository.WorkbooksRepository;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.serializer.WorkbooksSerializer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class WorkbooksService {

  @Autowired
  WorkbooksRepository workbooksRepository;

  public void addWorkbook(
      AddWorkbookDto addWorkbookDto,
      Long userId
  ) {
    int maxOrderNumber = getMaxOrderNumber(userId);
    WorkbooksEntity workbooksEntity = WorkbooksSerializer.convert(
        addWorkbookDto,
        userId,
        maxOrderNumber
    );
    WorkbooksEntity save = workbooksRepository.save(workbooksEntity);
    int id = save.getId();
    log.info("add new workbook for user {}, with id {}",userId,id );
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
        .collect(Collectors.toList());  }
}
