package com.zapiszto.controllers.clientBodyTest.service;

import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.dto.NewClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import com.zapiszto.controllers.clientBodyTest.repository.ClientBodyTestsRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClientBodyTestService {

  @Autowired
  ClientBodyTestsRepository clientBodyTestsRepository;

  public void addClientBodyTest(NewClientBodyTestDto newClientBodyTestDto) {
    ClientBodyTestsEntity clientBodyTestsEntity = ClientBodyTestsEntity.builder()
        .id(newClientBodyTestDto.getId())
        .clientId(newClientBodyTestDto.getClientId())
        .dictBodyTestId(newClientBodyTestDto.getDictBodyTestId())
        .result(newClientBodyTestDto.getResult())
        .build();
    clientBodyTestsRepository.save(clientBodyTestsEntity);
    log.error("Add new client body test with id: {}", newClientBodyTestDto.getId());

  }
}
