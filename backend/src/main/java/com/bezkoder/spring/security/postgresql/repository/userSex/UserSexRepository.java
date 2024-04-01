package com.bezkoder.spring.security.postgresql.repository.userSex;

import com.bezkoder.spring.security.postgresql.controllers.userSex.entity.UserSexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSexRepository extends JpaRepository<UserSexEntity, Integer> {

  UserSexEntity findByUserId(Long userId);
}
