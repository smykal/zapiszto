package com.zapiszto.controllers.program.clientProgramView.repository;

import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProgramRepository extends JpaRepository<MacrocycleEntity, UUID> {
  @Query(nativeQuery = true, value = """
      SELECT m.* FROM Macrocycle m
      			left join programs_details pd ON pd.id = m.program_id
      			left join clients c on c.id = pd.assigned_client
      			where c.id = :clientId or c.user_id = :userId
      """)
  List<MacrocycleEntity> getAllForClientOrUser(@Param("clientId") UUID clientId,
                                               @Param("userId") Long userId);
}
