package com.bezkoder.spring.security.postgresql.controllers.trainings.repository;

import com.bezkoder.spring.security.postgresql.controllers.trainings.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingsRepository extends JpaRepository<TrainingEntity, Integer> {
}
