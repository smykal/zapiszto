package com.zapiszto.controllers.userDetails.serializer;

import com.zapiszto.controllers.userDetails.dto.UserDetailsDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsSexDto;
import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserDetailsSerializer {
  public UserDetailsDto convert(UserDetailsEntity userDetailsEntity) {
    return UserDetailsDto.builder()
        .languageCode(userDetailsEntity.getDictLanguagesEntity().getCode())
        .languageLabel(userDetailsEntity.getDictLanguagesEntity().getLabel())
        .build();
  }

  public UserDetailsSexDto convert(String gender) {
    return UserDetailsSexDto.builder().gender(gender).build();
  }
}
