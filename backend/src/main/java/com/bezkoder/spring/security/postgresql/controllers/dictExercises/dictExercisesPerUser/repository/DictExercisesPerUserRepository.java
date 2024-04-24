package com.bezkoder.spring.security.postgresql.controllers.dictExercises.dictExercisesPerUser.repository;

import com.bezkoder.spring.security.postgresql.controllers.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictExercisesPerUserRepository extends JpaRepository<DictExercisesPerUserEntity, Integer> {
}
