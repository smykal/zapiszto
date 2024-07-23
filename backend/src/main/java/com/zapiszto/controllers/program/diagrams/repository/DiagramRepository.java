package com.zapiszto.controllers.program.diagrams.repository;

import com.zapiszto.controllers.program.diagrams.dto.ExerciseStatsDto;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagramRepository extends JpaRepository<MesocycleEntity, UUID> {

  @Query(nativeQuery = true, value = """
      select m.order_id as orderId,
             coalesce(dcb."name", dcpu."name") as category,
             sum(e.sets * e.quantity) as repetitions,
             sum(e.sets) as sets
      from exercises e 
      left join sessions s on e.session_id = s.id 
      left join microcycle m on s.microcycle_id = m.id 
      left join dict_exercises de ON e.dict_exercise_id = de.id 
      left join dict_exercises_basic deb on de.dict_exercises_basic_id = deb.id 
      left join dict_exercises_per_user depu on de.dict_exercises_per_user_id = depu.id 
      left join dict_category dc on coalesce(deb.dict_category_id, depu.dict_category_id) = dc.id
      left join dict_category_basic dcb on dc.dict_category_basic_id = dcb.id 
      left join dict_category_per_user dcpu on dc.dict_category_per_user_id = dcpu.id 
      where m.mesocycle_id = :mesocycleId
      group by m.id, coalesce(dcb."name", dcpu."name")
      order by coalesce(dcb."name", dcpu."name"), m.order_id
      """)
  List<Object[]> getExerciseStatsByMesocycleId(@Param("mesocycleId") UUID mesocycleId);
}
