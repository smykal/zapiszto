package com.zapiszto.controllers.dictUnits.repository;

import com.zapiszto.controllers.dictUnits.entity.DictUnitsEntity;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictUnitsRepository extends JpaRepository<DictUnitsEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select *
      		from (   select 	du.id
                         , 	du.dict_units_basic_id
                         ,	du.dict_units_per_user_id
                         ,	dupu.user_id
                         from dict_units du
                         left join dict_units_per_user dupu on dupu.id = du.dict_units_per_user_id
            ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictUnitsEntity> getAllForUser(@Param("userId") Long userId);
}
