package com.zapiszto.controllers.exercises.repository;

import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select e.*  from exercises e
                left join trainings t on t.id = e.training_id
                left join workbooks w on w.id = t.workbooks_id
                          where w.user_id = :userId
                          and e.training_id = :trainingId
      """)
  List<ExerciseEntity> getExercisesByTrainingId(
      @Param("userId") Long userId,
      @Param("trainingId") int trainingId
  );
}
