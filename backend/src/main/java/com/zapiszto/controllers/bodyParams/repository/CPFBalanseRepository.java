package com.zapiszto.controllers.bodyParams.repository;

import com.zapiszto.controllers.bodyParams.entity.BodyParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CPFBalanseRepository extends JpaRepository<BodyParamsEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select *
              from body_params bp
              where bp.dict_body_params_id = 4
              and user_id = :userId
              order by insert_date desc
              limit 1
      """)
  BodyParamsEntity getActualWeightForUser(@Param("userId") long userId);
}
