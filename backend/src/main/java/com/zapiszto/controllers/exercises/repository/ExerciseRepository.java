package com.zapiszto.controllers.exercises.repository;

import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Integer> {
}
