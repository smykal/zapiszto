package com.zapiszto.controllers.exercises.repository;

import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseSessionRepository extends JpaRepository<ExerciseEntity, UUID> {

  @Query(nativeQuery = true, value = """
      select COALESCE(max(e.order_number), 0) from exercises e where session_id =:sessionId
      """)
  Integer getOrderNumber(@Param("sessionId") UUID sessionId);

  @Query(nativeQuery = true, value = """
      select * from exercises e where e.session_id =:sessionId order by order_number
      """)
  List<ExerciseEntity> getAllBySessionId(@Param("sessionId") UUID sessionId);
}
