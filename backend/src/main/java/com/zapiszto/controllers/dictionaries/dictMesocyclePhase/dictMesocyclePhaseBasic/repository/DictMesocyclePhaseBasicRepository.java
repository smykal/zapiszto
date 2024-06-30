package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhaseBasic.repository;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhaseBasic.entity.DictMesocyclePhaseBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMesocyclePhaseBasicRepository extends JpaRepository<DictMesocyclePhaseBasicEntity, Integer> {
}
