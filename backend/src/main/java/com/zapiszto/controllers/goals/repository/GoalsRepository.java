package com.zapiszto.controllers.goals.repository;

import com.zapiszto.controllers.goals.entity.GoalEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalsRepository extends JpaRepository<GoalEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select * from goals g
      where g.client_id = :clientId
      """)
  List<GoalEntity> getGoalsByClientId(
      @Param("clientId") UUID clientId);
}
