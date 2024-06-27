package com.zapiszto.controllers.program.mesocycle.repository;

import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesocycleRepository extends JpaRepository<MesocycleEntity, UUID> {
  @Query(nativeQuery = true, value = """
      select * from mesocycle m where m.macrocycle_id = :macrocycleId
      """)
  List<MesocycleEntity> getMesocyclesByMacrocycleId(@Param("macrocycleId") UUID macrocycleId);

  @Query(nativeQuery = true, value = """
      select max(m.order_id) from mesocycle m where m.macrocycle_id = :macrocycleId
      """)
  Optional<Integer> findMaxOrderIdByMacrocycleId(@Param("macrocycleId") UUID macrocycleId);
}
