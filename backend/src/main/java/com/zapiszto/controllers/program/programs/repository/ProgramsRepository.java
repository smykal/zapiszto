package com.zapiszto.controllers.program.programs.repository;

import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProgramsRepository extends JpaRepository<ProgramEntity, Integer> {
  @Query(nativeQuery = true, value = """
      select * from programs p where p.trainer_id = :trainerId
      """)
  List<ProgramEntity> getAllByTrainerId(long trainerId);

  @Query(nativeQuery = true, value = """
      select * from programs p where p.id = :id
      """)
  Optional<ProgramEntity> findByUuid(UUID id);

  boolean existsById(UUID id);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = """
      DELETE FROM public.programs
      WHERE id=:programId
      """)
  void deleteByProgramId(@Param("programId") UUID programId);
}
