package com.zapiszto.controllers.clientBodyTest.service;

import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.dto.NewClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import com.zapiszto.controllers.clientBodyTest.repository.ClientBodyTestsRepository;
import com.zapiszto.controllers.clientBodyTest.serializer.ClientBodyTestSerializer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
        .id(newClientBodyTestDto.id())
        .clientId(newClientBodyTestDto.clientId())
        .dictBodyTestId(newClientBodyTestDto.dictBodyTestId())
        .result(newClientBodyTestDto.result())
        .build();
    clientBodyTestsRepository.save(clientBodyTestsEntity);
    log.error("Add new client body test with id: {}", newClientBodyTestDto.id());

  }

  public List<ClientBodyTestDto> getClientBodyTests(UUID clientId) {

    List<ClientBodyTestsEntity> allByClientId = clientBodyTestsRepository.getAllByClientId(clientId);
    return allByClientId.stream().map(ClientBodyTestSerializer::convert).collect(Collectors.toList());

  }
}
