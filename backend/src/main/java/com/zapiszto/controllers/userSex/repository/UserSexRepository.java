package com.zapiszto.controllers.userSex.repository;

import com.bezkoder.spring.security.postgresql.controllers.userSex.entity.UserSexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSexRepository extends JpaRepository<UserSexEntity, Integer> {

  UserSexEntity findByUserId(Long userId);

  @Query(nativeQuery = true, value = """
      SELECT ds."name"
              FROM user_sex us
              LEFT JOIN dict_sex ds ON us.dict_sex_id = ds.id
              WHERE us.user_id = :userId
      """)
  String findGenderByUserId(@Param("userId")Long userId);
}
