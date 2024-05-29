package com.zapiszto.controllers.clients.serializer;

import com.zapiszto.controllers.clients.dto.ClientDto;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ClientsSerializer {

  public ClientDto convert(ClientEntity clientEntity) {
    return ClientDto.builder()
        .id(clientEntity.getId())
        .clientName(clientEntity.getClientName())
        .userId(clientEntity.getUserId())
        .build();
  }
}
