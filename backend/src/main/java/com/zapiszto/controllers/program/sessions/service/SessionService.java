package com.zapiszto.controllers.program.sessions.service;

import com.zapiszto.controllers.program.macrocycle.dto.NewMacrocycleDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.sessions.dto.SessionDto;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import com.zapiszto.controllers.program.sessions.repository.SessionRepository;
import com.zapiszto.controllers.program.sessions.serializer.SessionSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class SessionService {

  @Autowired
  SessionRepository sessionRepository;

  @Transactional
  public void addSessions(int sessionsForMicrocycle, UUID microcycleId, int sessionDuration) {

    try {
      int orderId = sessionRepository.findMaxOrderIdByMicrocycleId(microcycleId)
          .orElse(0);

      for (int i = 0; i < sessionsForMicrocycle; i++) {
        orderId++;
        SessionEntity sessionEntity = SessionEntity.builder()
            .id(UUID.randomUUID())
            .microcycleId(microcycleId)
            .orderId(orderId)
            .label(String.valueOf(sessionDuration))
            .build();
        sessionRepository.save(sessionEntity);
      }
    } catch (Exception e) {
      log.error("Error adding sessions for microcycle {}: {}", microcycleId, e.getMessage());
      throw new RuntimeException("Error adding sessions", e);
    }
  }

  public List<SessionDto> getSessions(UUID microcycleId) {
    List<SessionEntity> sessionsByMicrocycleId = sessionRepository.getSessionsByMicrocycleId(microcycleId);
    return sessionsByMicrocycleId.stream()
        .map(SessionSerializer::convert)
        .collect(Collectors.toList());
  }

  public List<SessionEntity> getAllSessions() {
    return sessionRepository.findAll();
  }

  public Optional<SessionEntity> getSessionById(UUID id) {
    return sessionRepository.findById(id);
  }

  public SessionEntity createSession(SessionEntity sessionEntity) {
    return sessionRepository.save(sessionEntity);
  }

  public SessionEntity updateSession(UUID id, SessionEntity sessionEntity) {
    if (sessionRepository.existsById(id)) {
      sessionEntity.setId(id);
      return sessionRepository.save(sessionEntity);
    } else {
      throw new IllegalArgumentException("Session not found");
    }
  }

  public void deleteSession(UUID id) {
    sessionRepository.deleteById(id);
  }

  public List<SessionEntity> addSessions(MicrocycleEntity microcycleEntity, NewMacrocycleDto newMacrocycleDto) {
    UUID microcycleId = microcycleEntity.getId();
    int sessionsForMicrocycle = newMacrocycleDto.getSessionsForMicrocycle();
    int sessionDuration = newMacrocycleDto.getDurationSession();
    int orderId = sessionRepository.findMaxOrderIdByMicrocycleId(microcycleId)
        .orElse(0);

    List<SessionEntity> sessions = new ArrayList<>();

    for (int i = 0; i < sessionsForMicrocycle; i++) {
      orderId++;
      SessionEntity sessionEntity = SessionEntity.builder()
          .id(UUID.randomUUID())
          .microcycleId(microcycleId)
          .orderId(orderId)
          .label(String.valueOf(sessionDuration))
          .build();
      sessions.add(sessionEntity);
    }
    List<SessionEntity> sessionEntities = sessionRepository.saveAll(sessions);
    return sessionEntities;
  }
}
