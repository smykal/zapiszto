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
  Optional<UUID> findNextSessionId(@Param("currentSessionId") UUID currentSessionId);
}
