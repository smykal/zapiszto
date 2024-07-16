package com.zapiszto.controllers.dictionaries.dictEquipment.repository;

import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictEquipmentRepository extends JpaRepository<DictEquipmentEntity, UUID> {
  @Query(
      nativeQuery = true, value = """
      select *
      		from (   select 	du.id
                         , 	du.dict_equipment_basic_id
                         ,	du.dict_equipment_per_user_id
                         ,	dupu.user_id
                         from dict_equipment du
                         left join dict_equipment_per_user dupu on dupu.id = du.dict_equipment_per_user_id
            ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictEquipmentEntity> getAllForUser(@Param("userId") Long userId);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_equipment
        WHERE dict_equipment_per_user_id = :itemToDelete
      """)
  void deleteDictEquipmentPerUser(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_equipment_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictEquipmentPerUser(
      @Param("itemToDelete") int itemToDelete,
      @Param("userId") Long userId
  );

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_equipment
        WHERE dict_equipment_basic_id = :itemToDelete
      """)
  void deleteDictEquipmentBasic(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_equipment_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictEquipment(@Param("itemToDelete") int itemToDelete);
}
