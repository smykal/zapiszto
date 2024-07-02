package com.zapiszto.controllers.program.sessions.serializer;

import com.zapiszto.controllers.program.sessions.dto.SessionDto;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SessionSerializer {

  public static SessionDto convert(SessionEntity sessionEntity) {
    return SessionDto.builder()
        .id(sessionEntity.getId().toString())
        .microcycleId(sessionEntity.getMicrocycleId().toString())
        .orderId(sessionEntity.getOrderId())
        .label(sessionEntity.getLabel())
        .build();
  }
}