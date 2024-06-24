package com.zapiszto.controllers.goals.repository;

import com.zapiszto.controllers.goals.dto.GoalDetailsDto;
import com.zapiszto.controllers.goals.entity.GoalEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalsRepository extends JpaRepository<GoalEntity, Integer> {

  @Query(
      nativeQuery = true, value = """
      select * from goals g
      where g.client_id = :clientId
      """)
  List<GoalEntity> getGoalsByClientId(
      @Param("clientId") UUID clientId
  );

  @Query(
      nativeQuery = true, value = """
      select 	g.client_id as clientId,
      		dbp.name as bodyParamName,
      		coalesce(dbtb.name, dbtpu.name) as bodyTestName,
      		coalesce(dub.name, dupu.name) as unitName,
      		g.action,
      		g.value
      from goals g
      left join dict_body_params dbp on g.dict_body_param_id = dbp.id
      left join dict_body_test dbt on g.dict_body_test_id = dbt.id
      left join dict_body_test_basic dbtb on dbt.dict_body_test_basic_id = dbtb.id
      left join dict_body_test_per_user dbtpu on dbt.dict_body_test_per_user_id = dbtpu.id
      left join dict_units du on g.dict_unit_id = du.id
      left join dict_units_basic dub on du.dict_units_basic_id = dub.id
      left join dict_units_per_user dupu on du.dict_units_per_user_id = dupu.id
      where g.client_id = :clientId
      """)
  List<Object[]> findGoalDetailsByClientId(@Param("clientId") UUID clientId);

}
