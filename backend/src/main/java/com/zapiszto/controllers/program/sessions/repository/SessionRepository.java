package com.zapiszto.controllers.program.sessions.repository;

import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
  @Query(
      nativeQuery = true, value = """
      select * from sessions s where s.microcycle_id = :microcycleId
      """)
  List<SessionEntity> getSessionsByMicrocycleId(@Param("microcycleId") UUID microcycleId);

  @Query(
      nativeQuery = true, value = """
      select coalesce(max(s.order_id), 0) from sessions s where s.microcycle_id = :microcycleId
      """)
  Optional<Integer> findMaxOrderIdByMicrocycleId(@Param("microcycleId") UUID microcycleId);

  @Query("SELECT s.id FROM SessionEntity s WHERE s.microcycleId = :microcycleId AND s.orderId = :orderId")
  Optional<UUID> getNextByMicrocycleIdAndOrderId(@Param("microcycleId") UUID microcycleId, @Param("orderId") int orderId);

  @Query("SELECT ss.id " +
      "FROM SessionEntity ss " +
      "LEFT JOIN MicrocycleEntity m ON ss.microcycleId = m.id " +
      "WHERE m.mesocycleId IN (" +
      "    SELECT mi.mesocycleId " +
      "    FROM MicrocycleEntity mi " +
      "    LEFT JOIN SessionEntity s ON s.microcycleId = mi.id " +
      "    WHERE s.id = :sessionId" +
      ") " +
      "AND m.orderId IN (" +
      "    SELECT mi.orderId + 1 " +
      "    FROM MicrocycleEntity mi " +
      "    LEFT JOIN SessionEntity s ON s.microcycleId = mi.id " +
      "    WHERE s.id = :sessionId" +
      ") " +
      "AND ss.orderId = 1")
  Optional<UUID> getFirstSessionOfNextMicrocycle(@Param("sessionId") UUID sessionId);
}
