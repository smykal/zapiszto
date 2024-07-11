package com.zapiszto.controllers.dictionaries.dictExercises.repository;

import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictExercisesRepository extends JpaRepository<DictExercisesEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select * from ( select 	de.id
                          	,	de.dict_exercises_basic_id
                          	,	de.dict_exercises_per_user_id
                          	,	depu.user_id
                          from dict_exercises de
                          			    left join dict_exercises_per_user depu on depu.id = de.dict_exercises_per_user_id
                          ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictExercisesEntity> getAllForUser(@Param("userId") Long userId);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_exercises
        WHERE dict_exercises_per_user_id = :itemToDelete
      """)
  void deleteDictExercisePerUser(@Param("itemToDelete") UUID itemToDelete);


  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_exercises_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictExercisePerUser(@Param("itemToDelete") UUID itemToDelete,
                                 @Param("userId") Long userId);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_exercises
        WHERE dict_exercises_basic_id = :itemToDelete
      """)
  void deleteDictExerciseBasic(@Param("itemToDelete") UUID itemToDelete);


  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_exercises_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictExercise(@Param("itemToDelete") UUID itemToDelete);
}
