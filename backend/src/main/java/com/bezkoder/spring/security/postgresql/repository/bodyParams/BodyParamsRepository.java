package com.bezkoder.spring.security.postgresql.repository.bodyParams;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.entity.BodyParamsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyParamsRepository extends JpaRepository<BodyParamsEntity, Integer> {

  @Query(nativeQuery = true, value = "SELECT * from body_params where user_id = :userId")
  List<BodyParamsEntity> getAllById(
      @Param("userId") Long userId
  );

  @Query(nativeQuery = true, value = "WITH ranked_rows AS (\n" +
      "  SELECT\n" +
      "    bp.id,\n" +
      "    dbp.id  as dict_body_params_id,\n" +
      "    value ,\n" +
      "    user_id,\n" +
      "    insert_date,\n" +
      "    ROW_NUMBER() OVER (PARTITION BY dict_body_params_id ORDER BY insert_date DESC) AS row_num\n" +
      "  FROM\n" +
      "    public.body_params bp\n" +
      "    left join public.dict_body_params dbp on dbp.id = bp.dict_body_params_id\n" +
      ")\n" +
      "SELECT\n" +
      "  id,\n" +
      "  dict_body_params_id, \n" +
      "  value,\n" +
      "  user_id,\n" +
      "  insert_date\n" +
      "FROM\n" +
      "  ranked_rows\n" +
      "where 1=1\n" +
      " and row_num = 1\n" +
      " and user_id = :userId\n")
  List<BodyParamsEntity> getActualBodyParamsById(
      @Param("userId") Long userId
  );
}
