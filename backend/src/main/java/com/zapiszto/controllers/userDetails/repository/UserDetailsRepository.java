package com.zapiszto.controllers.userDetails.repository;

import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

  @Modifying
  @Transactional
  @Query(
      nativeQuery = true, value = """
      UPDATE  user_details
              SET dict_language_id= :languageId
              WHERE user_id= :userId
      """)
  void updateUserDetailsLanguage(
      @Param("userId") long userId,
      @Param("languageId") long languageId
  );

  UserDetailsEntity findByUserId(Long userId);

  @Transactional
  @Modifying
  @Query(
      nativeQuery = true, value = """
      UPDATE public.user_details
      SET dict_sex_id= :userSex
      WHERE user_id= :userId
      """)
  void updateSex(
      @Param("userSex") long userSex,
      @Param("userId") long userId
  );

  @Query(nativeQuery = true, value = """
      SELECT ds."name"
              FROM user_details ud
              LEFT JOIN dict_sex ds ON ud.dict_sex_id = ds.id
              WHERE ud.user_id = :userId
      """)
  String findGenderByUserId(@Param("userId")Long userId);

  @Query(nativeQuery = true, value = """
      SELECT EXTRACT(YEAR FROM AGE(NOW(), birthdate)) AS age
      FROM user_details
      WHERE user_id = :userId
      """)
  Integer getAgeById(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query(
      nativeQuery = true, value = """
      UPDATE public.user_details
      SET birthdate= :userBirthdate
      WHERE user_id= :userId
      """)
  void updateBirthdate(
      @Param("userBirthdate") ZonedDateTime userBirthdate,
      @Param("userId") long userId
  );
}
