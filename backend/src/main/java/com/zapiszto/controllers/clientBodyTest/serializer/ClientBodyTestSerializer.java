package com.zapiszto.controllers.clientBodyTest.serializer;

import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import com.zapiszto.controllers.common.SerializerCommon;

public class ClientBodyTestSerializer implements SerializerCommon {

  public static ClientBodyTestDto convert(ClientBodyTestsEntity clientBodyTestsEntity) {

    if (clientBodyTestsEntity.getDictBodyTestEntity()
        .getDictBodyTestBasicEntity() != null) {
      return ClientBodyTestDto.builder()
          .id(clientBodyTestsEntity.getDictBodyTestId())
          .dict(BASIC)
          .dict_id(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestBasicEntity()
              .getId())
          .name(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestBasicEntity()
              .getName())
          .result(clientBodyTestsEntity.getResult())
          .description(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestBasicEntity()
              .getDescription())
          .purpose(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestBasicEntity()
              .getPurpose())
          .build();
    } else {
      return ClientBodyTestDto.builder()
          .id(clientBodyTestsEntity.getDictBodyTestId())
          .dict(PER_USER)
          .dict_id(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestPerUserEntity()
              .getId())
          .name(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestPerUserEntity()
              .getName())
          .result(clientBodyTestsEntity.getResult())
          .description(clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestPerUserEntity()
              .getDescription())
          .purpose((clientBodyTestsEntity.getDictBodyTestEntity()
              .getDictBodyTestPerUserEntity()
              .getPurpose()))
          .build();
    }
  }
}
