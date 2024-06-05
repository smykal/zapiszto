package com.zapiszto.controllers.goals.repository;

import com.zapiszto.controllers.goals.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalsRepository extends JpaRepository<GoalEntity, Integer> {
}
