package com.zapiszto.controllers.program.microcycle.repository;

import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MicrocycleRepository extends JpaRepository<MicrocycleEntity,Integer> {
  @Query(nativeQuery = true, value = """
      select * from microcycle m where m.mesocycle_id = :mesocycleId
      """)
  List<MicrocycleEntity> getMicrocyclesByMesocycleId(@Param("mesocycleId") UUID mesocycleId);

  @Query(nativeQuery = true, value = """
      select coalesce(max(m.order_id), 0) from microcycle m where m.mesocycle_id = :mesocycleId
      """)
  Optional<Integer> findMaxOrderIdByMesocycleId(@Param("mesocycleId") UUID mesocycleId);
}
