package com.zapiszto.controllers.dictionaries.dictSessionPart.repository;

import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictSessionPartRepository extends JpaRepository<DictSessionPartEntity, UUID> {

  @Query(
      nativeQuery = true, value = """
      select *
      		from (   select 	du.id
                         , 	du.dict_session_part_basic_id
                         ,	du.dict_session_part_per_user_id
                         ,	dupu.user_id
                         from dict_session_part du
                         left join dict_session_part_per_user dupu on dupu.id = du.dict_session_part_per_user_id
            ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictSessionPartEntity> getAllForUser(@Param("userId") Long userId);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_session_part
        WHERE dict_session_part_per_user_id = :itemToDelete
      """)
  void deleteDictSessionPartPerUser(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_session_part_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictSessionPartPerUser(
      @Param("itemToDelete") int itemToDelete,
      @Param("userId") Long userId
  );

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_session_part
        WHERE dict_session_part_basic_id = :itemToDelete
      """)
  void deleteDictSessionPartBasic(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_session_part_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictSessionPart(@Param("itemToDelete") int itemToDelete);
}
