package com.zapiszto.controllers.userDetails.service;

import com.zapiszto.controllers.userDetails.dictLanguages.entity.DictLanguagesEntity;
import com.zapiszto.controllers.userDetails.dictLanguages.repository.DictLanguagesRepository;
import com.zapiszto.controllers.userDetails.dictSex.entity.DictSexEntity;
import com.zapiszto.controllers.userDetails.dictSex.repository.DictSexRepository;
import com.zapiszto.controllers.userDetails.dto.UserDetailsAgeDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsBirthdateDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsGenderDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsLanguageDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsSexDto;
import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.userDetails.serializer.UserDetailsSerializer;
import java.time.ZonedDateTime;
import java.util.List;
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
  private DictSexRepository dictSexRepository;

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

  public void saveUserSex(
      Long userId,
      UserDetailsGenderDto gender
  ) {
    List<DictSexEntity> dictSexRepositoryAll = dictSexRepository.findAll();
    for (DictSexEntity dictSex :
        dictSexRepositoryAll) {
      if (dictSex.getName()
          .equalsIgnoreCase(gender.getGender())) {
        userDetailsRepository.updateSex(dictSex.getId(), userId);
        log.info("save user sex for user: {} as {}", userId, gender.getGender());
        return;
      }
    }
  }

  public UserDetailsSexDto getUserSex(Long userId) {
    var userSex = userDetailsRepository.findGenderByUserId(userId);
    log.info("get user sex for user: {}", userId);
    return userDetailsSerializer.convert(userSex);
  }

  public UserDetailsAgeDto getUserAge(Long userId) {
    Integer ageById = userDetailsRepository.getAgeById(userId);
    log.info("get user age for user: {}", userId);
    return new UserDetailsAgeDto(ageById);

  }

  public void saveUserBirthDate(Long userId, UserDetailsBirthdateDto userBirthdateDto) {
    ZonedDateTime birthDate = ZonedDateTime.parse(userBirthdateDto.getBirthDate());

    userDetailsRepository.updateBirthdate(birthDate, userId);
    log.info("save birthdate for user: {}", userId);
  }
}

