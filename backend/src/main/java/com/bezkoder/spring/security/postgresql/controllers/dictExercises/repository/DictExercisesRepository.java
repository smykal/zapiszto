package com.bezkoder.spring.security.postgresql.controllers.dictExercises.repository;

import com.bezkoder.spring.security.postgresql.controllers.dictExercises.entity.DictExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictExercisesRepository extends JpaRepository<DictExercisesEntity, Integer> {
}
