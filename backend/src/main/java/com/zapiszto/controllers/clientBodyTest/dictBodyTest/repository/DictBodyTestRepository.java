package com.zapiszto.controllers.clientBodyTest.dictBodyTest.repository;

import com.zapiszto.controllers.clientBodyTest.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.dictCategory.entity.DictCategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictBodyTestRepository extends JpaRepository<DictBodyTestEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select * from ( select 	de.id
                          	,	de.dict_body_test_basic_id
                          	,	de.dict_body_test_per_user_id
                          	,	depu.user_id
                          from dict_body_test de
                          			    left join dict_body_test_per_user depu on depu.id = de.dict_body_test_per_user_id
                          ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictBodyTestEntity> getAllForUser(@Param("userId") Long userId);
}
