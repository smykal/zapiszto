package com.zapiszto.controllers.dictExercises.dictExercisesPerUser.repository;

import com.zapiszto.controllers.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictExercisesPerUserRepository extends JpaRepository<DictExercisesPerUserEntity, Integer> {
}
