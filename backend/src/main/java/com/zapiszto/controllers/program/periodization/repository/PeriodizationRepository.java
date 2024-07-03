package com.zapiszto.controllers.program.periodization.repository;

import com.zapiszto.controllers.program.periodization.entity.PeriodizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface PeriodizationRepository extends JpaRepository<PeriodizationEntity, Integer> {

  @Query(nativeQuery = true, value = """
      SELECT DISTINCT p.name, p.description FROM periodization p
      """)
  List<Object[]> findDistinctNameAndDescription();

  @Query(nativeQuery = true, value = """
      select distinct p.dict_mesocycle_phase_id from periodization p 
      where p.name = :periodizationName
      """)
  Integer getMesocyclePhase(@Param("periodizationName") String periodizationName);
}