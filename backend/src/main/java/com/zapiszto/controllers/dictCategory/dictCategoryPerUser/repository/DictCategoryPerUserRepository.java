package com.zapiszto.controllers.dictCategory.dictCategoryPerUser.repository;

import com.zapiszto.controllers.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import com.zapiszto.controllers.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictCategoryPerUserRepository extends JpaRepository<DictCategoryPerUserEntity, Integer> {
}
