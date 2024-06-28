package com.zapiszto.controllers.program.macrocycle.repository;

import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MacrocycleRepository extends JpaRepository<MacrocycleEntity, Integer> {
  @Query(nativeQuery = true, value = """
      select * from macrocycle m where m.program_id = :programId
      """)
  MacrocycleEntity getMacrocycleByProgramId(@Param("programId") UUID programId);

  @Query(nativeQuery = true, value = """
      select * from macrocycle m where m.id = :macrocycleId
      """)
  MacrocycleEntity getMacrocycleByMacrocycleId(@Param("macrocycleId") UUID macrocycleId);
}
