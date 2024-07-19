package com.zapiszto.controllers.program.microcycle.repository;

import com.zapiszto.controllers.program.microcycle.dto.MicrocycleStatsDto;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface MicrocycleRepository extends JpaRepository<MicrocycleEntity, UUID> {
  @Query(
      nativeQuery = true, value = """
      select * from microcycle m where m.mesocycle_id = :mesocycleId
      """)
  List<MicrocycleEntity> getMicrocyclesByMesocycleId(@Param("mesocycleId") UUID mesocycleId);

  @Query(
      nativeQuery = true, value = """
      select coalesce(max(m.order_id), 0) from microcycle m where m.mesocycle_id = :mesocycleId
      """)
  Optional<Integer> findMaxOrderIdByMesocycleId(@Param("mesocycleId") UUID mesocycleId);

  @Query(nativeQuery = true, value = """
      select 	coalesce(dcb."name", dcpu."name") 	as category
      	,	sum(e.sets * e.quantity) 			as repetitions
      	,	sum(e.sets)							as sets
      from exercises e
      		left join sessions s on e.session_id = s.id
      		left join dict_exercises de ON e.dict_exercise_id = de.id
      		left join dict_exercises_basic deb on de.dict_exercises_basic_id = deb.id
      		left join dict_exercises_per_user depu on de.dict_exercises_per_user_id = depu.id
      		left join dict_category dc on coalesce(deb.dict_category_id, depu.dict_category_id) = dc.id
      		left join dict_category_basic dcb on dc.dict_category_basic_id = dcb.id
      		left join dict_category_per_user dcpu on dc.dict_category_per_user_id = dcpu.id
      		where s.microcycle_id = :microcycleId
      		group by coalesce(dcb."name", dcpu."name")
      """)
  List<Object[]> findMicrocycleStats(@Param("microcycleId") UUID microcycleId);
}
