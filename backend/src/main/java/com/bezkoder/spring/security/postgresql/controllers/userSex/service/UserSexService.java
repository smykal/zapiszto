package com.bezkoder.spring.security.postgresql.controllers.userSex.service;

import com.bezkoder.spring.security.postgresql.controllers.userSex.dto.GenderDto;
import com.bezkoder.spring.security.postgresql.controllers.dictSex.entity.DictSexEntity;
import com.bezkoder.spring.security.postgresql.controllers.userSex.dto.UserSexDto;
import com.bezkoder.spring.security.postgresql.controllers.userSex.entity.UserSexEntity;
import com.bezkoder.spring.security.postgresql.controllers.userSex.serializer.UserSexSerializer;
import com.bezkoder.spring.security.postgresql.controllers.dictSex.repository.DictSexRepository;
import com.bezkoder.spring.security.postgresql.controllers.userSex.repository.UserSexRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserSexService {

  @Autowired
  private DictSexRepository dictSexRepository;

  @Autowired
  private UserSexRepository userSexRepository;

  @Autowired
  private UserSexSerializer userSexSerializer;

  public void saveUserSex(
      Long userId,
      GenderDto gender
  ) {
    List<DictSexEntity> dictSexRepositoryAll = dictSexRepository.findAll();
    for (DictSexEntity dictSex :
        dictSexRepositoryAll) {
      if (dictSex.getName().equalsIgnoreCase(gender.getGender())) {
        UserSexEntity userSex = new UserSexEntity(userId.intValue(), dictSex);
        userSexRepository.save(userSex);
        log.info("save user sex for user: {} as {}", userId, gender.getGender());
        return;
      }
    }
  }

  public UserSexDto getUserSex(Long userId) {
    UserSexEntity userSex = userSexRepository.findByUserId(userId);
    log.info("get user sex for user: {}", userId);
    return userSexSerializer.convert(userSex);
  }
}
