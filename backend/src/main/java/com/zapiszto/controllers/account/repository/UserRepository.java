package com.zapiszto.controllers.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zapiszto.controllers.account.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  Optional<User> findByResetToken(String resetToken);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  void deleteById(Long userId);

  @Query(nativeQuery = true, value = """
      select id
            from users where upper(email) = upper(:email)
      """)
  Long idByEmail(@Param("email") String email);

  @Query(nativeQuery = true, value = """
      select r.name
      from user_roles ur
      left join roles r on ur.role_id = r.id
      where ur.user_id = :userId
      """)
  String roleById(@Param("userId") Long userId);
}
