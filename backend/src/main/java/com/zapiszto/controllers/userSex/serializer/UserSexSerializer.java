package com.bezkoder.spring.security.postgresql.controllers.userSex.serializer;

import com.bezkoder.spring.security.postgresql.controllers.userSex.dto.UserSexDto;
import com.bezkoder.spring.security.postgresql.controllers.userSex.entity.UserSexEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserSexSerializer {

  public UserSexDto convert(UserSexEntity userSexEntity) {
    return UserSexDto.builder().gender(userSexEntity.getDictSex().getName()).build();
  }
}
