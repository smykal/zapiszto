package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhasePerUser.repository;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhasePerUser.entity.DictMesocyclePhasePerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMesocyclePhasePerUserRepository extends JpaRepository<DictMesocyclePhasePerUserEntity, Integer> {
}
