package com.zapiszto.controllers.dictionaries.dictQuantityType.repository;

import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictQuantityTypeRepository extends JpaRepository<DictQuantityTypeEntity, UUID> {
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

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_quantity_type
        WHERE dict_quantity_type_per_user_id = :itemToDelete
      """)
  void deleteDictQuantityTypePerUser(@Param("itemToDelete") UUID itemToDelete);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_quantity_type_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictQuantityTypePerUser(@Param("itemToDelete") UUID itemToDelete,
                                     @Param("userId") Long userId);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_quantity_type_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictQuantityType(@Param("itemToDelete") UUID itemToDelete);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_quantity_type
        WHERE dict_exercises_basic_id = :itemToDelete
      """)
  void deleteDictQuantityTypeBasic(@Param("itemToDelete") UUID itemToDelete);

}
