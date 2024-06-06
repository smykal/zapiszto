package com.zapiszto.controllers.clients.service;

import com.zapiszto.controllers.clients.dto.ClientDto;
import com.zapiszto.controllers.clients.dto.NewClientDto;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.clients.repository.ClientsRepository;
import com.zapiszto.controllers.clients.serializer.ClientsSerializer;
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
public class ClientsService {

  @Autowired
  ClientsRepository clientsRepository;

  @Autowired
  ClientsSerializer clientsSerializer;

  public List<ClientDto> getClients(long trainerId) {
    List<ClientEntity> allByTrainerId = clientsRepository.getAllByTrainerId(trainerId);
    return allByTrainerId.stream()
        .map(clientsSerializer::convert)
        .collect(Collectors.toList());
  }

  public void addClient(NewClientDto newClientDto, long trainerId) {
    ClientEntity clientEntity = ClientEntity.builder()
        .id(UUID.fromString(newClientDto.getId()))
        .trainerId(trainerId)
        .clientName(newClientDto.getClientName())
        .userId(newClientDto.getUserId() == 0 ? null : newClientDto.getUserId())
        .build();
    clientsRepository.save(clientEntity);
  }

  public void updateClient(ClientDto clientDto) {
    try {
      ClientEntity clientEntity = clientsRepository.getByIdUuid(clientDto.getId().toString());
      clientEntity.setClientName(clientDto.getClientName());
      clientEntity.setUserId(clientDto.getUserId());
      clientsRepository.save(clientEntity);
    } catch (RuntimeException e) {
      log.error("Error updating client: {}", e.getMessage());
      throw e;
    }
  }
 }
