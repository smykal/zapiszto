package com.zapiszto.controllers.trainings.repository;

import com.bezkoder.spring.security.postgresql.controllers.trainings.entity.TrainingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingsRepository extends JpaRepository<TrainingEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select t.*  from trainings t
      		        left join workbooks w on w.id = t.workbooks_id
      		        where w.user_id = :userId
      		        and w.id = :workbookId
      """)
  List<TrainingEntity> getTrainings(
      @Param("userId") Long userId,
      @Param("workbookId") int workbookId
  );
}
