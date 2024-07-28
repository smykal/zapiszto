package com.zapiszto.controllers.program.sessions.repository;

import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
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

  @Query(nativeQuery = true, value = """
        select s.id from sessions s 
        left join microcycle m on s.microcycle_id = m.id 
        left join mesocycle m2 on m.mesocycle_id = m2.id 
        WHERE m2.macrocycle_id in (
            select m2.macrocycle_id from sessions s 
            left join microcycle m on s.microcycle_id = m.id 
            left join mesocycle m2 on m.mesocycle_id = m2.id 
            WHERE s.id = :currentSessionId
        )
        and m.order_id in (
            select m.order_id + 1 from sessions s 
            left join microcycle m on s.microcycle_id = m.id 
            WHERE s.id = :currentSessionId
        )
        and s.order_id in (
            select s.order_id from sessions s 
            WHERE s.id = :currentSessionId
        )
        """)
  Optional<UUID> findNextWeekSessionId(@Param("currentSessionId") UUID currentSessionId);

  @Query(nativeQuery = true, value = """
      			SELECT COALESCE(
          (SELECT s.id
           FROM sessions s
           WHERE s.microcycle_id = :currentMicrocycleId
             AND s.order_id = :orderId + 1),
          (SELECT ss.id
           FROM sessions ss
           LEFT JOIN microcycle m ON ss.microcycle_id = m.id
           WHERE m.mesocycle_id IN (
                 SELECT mi.mesocycle_id
                 FROM microcycle mi
                 LEFT JOIN sessions s ON s.microcycle_id = mi.id
                 WHERE s.id = :currentMicrocycleId
           )
           AND m.order_id IN (
                 SELECT mi.order_id + 1
                 FROM microcycle mi
                 LEFT JOIN sessions s ON s.microcycle_id = mi.id
                 WHERE s.id = :currentMicrocycleId
           )
           AND ss.order_id = 1)
      ) AS next_session_id
      """)
  Optional<UUID> findNextWeekSessionId(@Param("currentMicrocycleId") UUID currentMicrocycleId,
                                        @Param("orderId") int orderId );
}

