package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.repository;


import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.entity.DictMicrocyclePhaseEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMicrocyclePhaseRepository extends JpaRepository<DictMicrocyclePhaseEntity, Integer> {
  @Query(
      nativeQuery = true, value = """
      select *
      		from (   select 	du.id
                         , 	du.dict_microcycle_phase_basic_id
                         ,	du.dict_microcycle_phase_per_user_id
                         ,	dupu.user_id
                         from dict_microcycle_phase du
                         left join dict_microcycle_phase_per_user dupu on dupu.id = du.dict_microcycle_phase_per_user_id
            ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictMicrocyclePhaseEntity> getAllForUser(@Param("userId") Long userId);

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_microcycle_phase
        WHERE dict_microcycle_phase_per_user_id = :itemToDelete
      """)
  void deleteDictMicrocyclePhasePerUser(@Param("itemToDelete") int itemToDelete);

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_microcycle_phase_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictMicrocyclePhasePerUser(
      @Param("itemToDelete") int itemToDelete,
      @Param("userId") Long userId
  );

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_microcycle_phase_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictMicrocyclePhase(@Param("itemToDelete") int itemToDelete);

  @Modifying
  @Query(
      nativeQuery = true, value = """
      DELETE FROM public.dict_microcycle_phase
        WHERE dict_exercises_basic_id = :itemToDelete
      """)
  void deleteDictMicrocyclePhaseBasic(@Param("itemToDelete") int itemToDelete);

}
