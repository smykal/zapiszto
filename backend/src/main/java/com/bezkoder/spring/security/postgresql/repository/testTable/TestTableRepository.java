package com.bezkoder.spring.security.postgresql.repository.testTable;

import com.bezkoder.spring.security.postgresql.controllers.testTable.entity.TestTableEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableRepository extends JpaRepository<TestTableEntity, Integer> {

  @Query(nativeQuery = true, value = "SELECT * from test_table")
  List<TestTableEntity> getAll();
}
