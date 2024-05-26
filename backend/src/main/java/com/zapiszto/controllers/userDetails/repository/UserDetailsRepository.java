package com.zapiszto.controllers.userDetails.repository;

import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
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

}
