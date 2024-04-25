package com.zapiszto.controllers.workbooks.repository;

import com.zapiszto.controllers.workbooks.entity.WorkbooksEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbooksRepository extends JpaRepository<WorkbooksEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select
      	max(order_number) + 1 as result
      from	workbooks
      where	user_id = :userId
      group by user_id
      """)
  int getActualOrderNumber(@Param("userId") Long userId);

  List<WorkbooksEntity> getAllByUserId(Long userId);

  void deleteByIdAndUserId(
      Long id,
      Long userId
  );
}
