package com.zapiszto.controllers.dictCategory.repository;

import com.zapiszto.controllers.dictCategory.entity.DictCategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictCategoryRepository extends JpaRepository<DictCategoryEntity, Integer> {
  @Query(nativeQuery = true, value = """
      select * from ( select 	de.id
                          	,	de.dict_category_basic_id
                          	,	de.dict_category_per_user_id
                          	,	depu.user_id
                          from dict_category de
                          			    left join dict_category_per_user depu on depu.id = de.dict_category_per_user_id
                          ) as foo
            where user_id is null or user_id = :userId
      """)
  List<DictCategoryEntity> getAllForUser(@Param("userId") Long userId);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_category
        WHERE dict_category_per_user_id = :itemToDelete
      """)
  void deleteDictCategoryPerUser(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_category_per_user
        WHERE id = :itemToDelete
        AND user_id = :userId
      """)
  void deleteDictCategoryPerUser(@Param("itemToDelete") int itemToDelete,
                                 @Param("userId") Long userId);

  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_category
        WHERE dict_category_basic_id = :itemToDelete
      """)
  void deleteDictCategoryBasic(@Param("itemToDelete") int itemToDelete);


  @Modifying
  @Query(nativeQuery = true, value = """
      DELETE FROM public.dict_category_basic
        WHERE id = :itemToDelete
      """)
  void deleteDictCategory(@Param("itemToDelete") int itemToDelete);
}
