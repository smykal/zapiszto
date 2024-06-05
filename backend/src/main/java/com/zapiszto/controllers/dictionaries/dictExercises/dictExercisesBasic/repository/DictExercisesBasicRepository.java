package com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.repository;

import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictExercisesBasicRepository extends JpaRepository<DictExercisesBasicEntity, Integer> {
}
