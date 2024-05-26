package com.zapiszto.controllers.userDetails.service;

import com.zapiszto.controllers.userDetails.dictLanguages.entity.DictLanguagesEntity;
import com.zapiszto.controllers.userDetails.dictLanguages.repository.DictLanguagesRepository;
import com.zapiszto.controllers.userDetails.dto.UserDetailsDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsLanguageDto;
import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.userDetails.serializer.UserDetailsSerializer;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsService {

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Autowired
  DictLanguagesRepository dictLanguagesRepository;

  @Autowired
  private UserDetailsSerializer userDetailsSerializer;

  public UserDetailsDto getUserDetails(Long userId) {
    UserDetailsEntity userDetailsEntity = userDetailsRepository.getReferenceById(userId.intValue());
    return userDetailsSerializer.convert(userDetailsEntity);
  }

  public void saveOrUpdate(Long userId, UserDetailsLanguageDto userDetailsLanguageDto) {
    DictLanguagesEntity dictLanguagesEntity = dictLanguagesRepository.getByCode(userDetailsLanguageDto.getLanguageCode());

    Optional<UserDetailsEntity> existingUserDetails = Optional.ofNullable(userDetailsRepository.findByUserId(userId));

    if (existingUserDetails.isPresent()) {
      userDetailsRepository.updateUserDetailsLanguage(userId, dictLanguagesEntity.getId());
      log.info("updated user language");
    } else {
      UserDetailsEntity userDetailsEntity = UserDetailsEntity.builder()
          .userId(userId)
          .dictLanguagesEntity(dictLanguagesEntity)
          .build();
      userDetailsRepository.save(userDetailsEntity);
      log.info("init user language");
    }
  }
}

