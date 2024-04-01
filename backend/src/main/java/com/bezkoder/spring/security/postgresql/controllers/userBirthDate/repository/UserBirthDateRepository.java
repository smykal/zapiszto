package com.bezkoder.spring.security.postgresql.controllers.userBirthDate.repository;

import com.bezkoder.spring.security.postgresql.controllers.dictSex.entity.DictSexEntity;
import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.entity.UserBirthDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBirthDateRepository  extends JpaRepository<UserBirthDateEntity, Integer> {

  @Query(nativeQuery = true, value = """
      SELECT EXTRACT(YEAR FROM AGE(NOW(), birthdate)) AS age
      FROM user_birthdate
      WHERE user_id = :userId
      """)
  Integer getAgeById(@Param("userId") Long userId);
}
