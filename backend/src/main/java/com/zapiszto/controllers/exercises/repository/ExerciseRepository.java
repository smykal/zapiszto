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
                order by e.order_number asc
      """)
  List<ExerciseEntity> getExercisesByTrainingId(
      @Param("userId") Long userId,
      @Param("trainingId") int trainingId
  );

  @Query(nativeQuery = true, value = """
      select e.*
        from workbooks w
      		inner join trainings t on t.workbooks_id = w.id
      		inner join exercises e on e.training_id = t.id
      		where w.user_id = :userId
      		and e.training_id = :trainingId
      		and e.id = :exerciseId
      """)
  ExerciseEntity getByUserIdTrainingIdExerciseId(
      @Param("userId") Long userId,
      @Param("trainingId") int trainingId,
      @Param("exerciseId") int exerciseId
  );

  @Query(nativeQuery = true, value = """
      select case
      	when max(e.order_number) is null then 1
      	else max(e.order_number) + 1
      end
        from workbooks w
      		inner join trainings t on t.workbooks_id = w.id
      		inner join exercises e on e.training_id = t.id
      		where w.user_id = :userId
      		and e.training_id = :trainingId
      """)
  Integer getOrderNumber(@Param("userId") Long userId,
                         @Param("trainingId") int trainingId);

  List<ExerciseEntity> findByTrainingIdOrderByOrderNumber(int trainingId);
}
