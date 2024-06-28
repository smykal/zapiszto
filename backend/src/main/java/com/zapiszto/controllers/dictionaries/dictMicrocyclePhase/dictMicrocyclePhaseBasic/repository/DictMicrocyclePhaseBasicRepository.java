package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhaseBasic.repository;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhaseBasic.entity.DictMicrocyclePhaseBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMicrocyclePhaseBasicRepository extends JpaRepository<DictMicrocyclePhaseBasicEntity, Integer> {
}
