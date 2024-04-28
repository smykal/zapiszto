package com.zapiszto.controllers.bodyParams.repository;

import com.zapiszto.controllers.bodyParams.entity.BodyParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BmrRepository extends JpaRepository<BodyParamsEntity, Integer> {

  @Query(nativeQuery = true, value = """
      WITH height AS (
                      SELECT 	bp.value as height
                      		    ,	bp.user_id as user_id
                              FROM	body_params bp
                              WHERE	bp.dict_body_params_id = 9
                              ORDER BY	bp.insert_date desc
                              LIMIT 1 ),
      other_parameters as (
                          SELECT	bp.user_id as user_id
      		                ,	bp.value as weight
      		                ,	extract(year from	AGE(NOW(),	ub.birthdate)) as age
      		                ,	bp.insert_date as insert_date
                          FROM	body_params bp
                          LEFT JOIN user_sex us ON	bp.user_id = us.user_id
                          LEFT JOIN user_birthdate ub ON	bp.user_id = ub.user_id
                          ORDER BY	bp.insert_date desc
                          LIMIT 1 )
      SELECT  other_parameters.user_id
      		  ,	other_parameters.insert_date
      		  ,	other_parameters.age
      		  ,	other_parameters.weight
      		  ,	height.height
      		  FROM height
            LEFT JOIN other_parameters on height.user_id = other_parameters.User_id
            WHERE other_parameters.user_id = :userId
      """)
  String getBmrData(
      @Param("userId") Long userId
  );
}
