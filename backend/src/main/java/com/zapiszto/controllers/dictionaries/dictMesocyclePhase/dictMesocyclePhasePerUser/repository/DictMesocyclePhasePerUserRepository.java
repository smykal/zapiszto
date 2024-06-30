package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.repository;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.entity.DictMicrocyclePhasePerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMicrocyclePhasePerUserRepository extends JpaRepository<DictMicrocyclePhasePerUserEntity, Integer> {
}
