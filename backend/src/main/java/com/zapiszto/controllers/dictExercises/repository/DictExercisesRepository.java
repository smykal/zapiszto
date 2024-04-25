package com.zapiszto.controllers.dictExercises.repository;

import com.zapiszto.controllers.dictExercises.entity.DictExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictExercisesRepository extends JpaRepository<DictExercisesEntity, Integer> {
}
