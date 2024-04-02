package com.bezkoder.spring.security.postgresql.controllers.bodyParams.repository;

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

  @Query(nativeQuery = true, value = """
        WITH ranked_rows AS (
          SELECT
            bp.id,
            dbp.id  as dict_body_params_id,
            value ,
            user_id,
            insert_date,
            ROW_NUMBER() OVER (PARTITION BY dict_body_params_id ORDER BY insert_date DESC) AS row_num
          FROM
            public.body_params bp
            left join public.dict_body_params dbp on dbp.id = bp.dict_body_params_id
        )
        SELECT
          id,
          dict_body_params_id,
          value,
          user_id,
          insert_date
        FROM
          ranked_rows
        where 1=1
         and row_num = 1
         and user_id = :userId
        """)
  List<BodyParamsEntity> getActualBodyParamsById(
      @Param("userId") Long userId
  );

  @Query(nativeQuery = true, value = """
      select bp.value from body_params bp
            where bp.user_id = :userId
            and bp.dict_body_params_id = 9
            order by insert_date desc
            limit 1
      """)
  int getLastHeight(
      @Param("userId") Long userId
  );

  @Query(nativeQuery = true, value = """
      select * from body_params bp
      where bp.user_id = :userId
      and bp.dict_body_params_id = 4
      order by insert_date desc
      """)
  List<BodyParamsEntity> getWeight(@Param("userId") Long userId);
}
