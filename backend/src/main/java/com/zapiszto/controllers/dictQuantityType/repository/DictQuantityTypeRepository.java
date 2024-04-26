package com.zapiszto.controllers.dictQuantityType.repository;

import com.zapiszto.controllers.dictQuantityType.entity.DictQuantityTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictQuantityTypeRepository extends JpaRepository<DictQuantityTypeEntity, Integer> {
  @Query(nativeQuery = true, value = """
      select *
      		from (   select 	du.id
                         , 	du.dict_quantity_type_basic_id
                         ,	du.dict_quantity_type_per_user_id
                         ,	dupu.user_id
                         from dict_quantity_type du
                         left join dict_quantity_type_per_user dupu on dupu.id = du.dict_quantity_type_per_user_id
            ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictQuantityTypeEntity> getAllForUser(@Param("userId") Long userId);
}
