package com.zapiszto.controllers.dictExercises.dictExercisesBasic.repository;

import com.bezkoder.spring.security.postgresql.controllers.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictExercisesBasicRepository extends JpaRepository<DictExercisesBasicEntity, Integer> {
}
