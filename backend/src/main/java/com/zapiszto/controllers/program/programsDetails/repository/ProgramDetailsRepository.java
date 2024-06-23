package com.zapiszto.controllers.program.programsDetails.repository;

import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProgramDetailsRepository extends JpaRepository<ProgramDetailsEntity, Integer> {

  @Modifying
  @Query(
      nativeQuery = true, value = """
      UPDATE public.programs_details
      SET assigned_client= :assignedClient
      WHERE id=:programId
      """)
  void updateAssignedClient(
      @Param("programId") UUID programId,
      @Param("assignedClient") UUID assignedClient
  );

  @Query(nativeQuery = true, value = """
      select * from programs_details p where p.id = :id
      """)
  Optional<ProgramDetailsEntity> findByUuid(UUID id);

  boolean existsById(UUID id);


  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = """
      DELETE FROM public.programs_details
      WHERE id=:programId
      """)
  void deleteByProgramId(@Param("programId") UUID programId);
}
