package com.bezkoder.spring.security.postgresql.repository.bodyParams;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.entity.TestTableEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableRepository extends JpaRepository<TestTableEntity, Integer> {

  @Query(nativeQuery = true, value = "SELECT * from test_table where user_id = :userId")
  List<TestTableEntity> getAllById(
      @Param("userId") Long userId
  );

  @Query(nativeQuery = true, value = "WITH ranked_rows AS (\n" +
      "  SELECT\n" +
      "    id,\n" +
      "    kolumna_1,\n" +
      "    kolumna_2,\n" +
      "    user_id,\n" +
      "    insert_date,\n" +
      "    ROW_NUMBER() OVER (PARTITION BY kolumna_1 ORDER BY insert_date DESC) AS row_num\n" +
      "  FROM\n" +
      "    public.test_table\n" +
      ")\n" +
      "SELECT\n" +
      "  id,\n" +
      "  kolumna_1,\n" +
      "  kolumna_2,\n" +
      "  user_id\n" +
      "FROM\n" +
      "  ranked_rows\n" +
      "WHERE\n" +
      "  row_num = 1 and user_id = :userId")
  List<TestTableEntity> getActualBodyParamsById(
      @Param("userId") Long userId
  );
}
