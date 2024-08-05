package com.zapiszto.controllers.clientBodyTest.serializer;

import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import com.zapiszto.controllers.common.SerializerCommon;

public class ClientBodyTestSerializer implements SerializerCommon {

  public static ClientBodyTestDto convert(ClientBodyTestsEntity clientBodyTestsEntity) {

    if (clientBodyTestsEntity.getDictBodyTestEntity()
        .getDictBodyTestBasicEntity() != null) {
      return new ClientBodyTestDto(
          clientBodyTestsEntity.getId(),
          clientBodyTestsEntity.getDictBodyTestId(),
          BASIC,
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestBasicEntity().getId(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestBasicEntity().getName(),
          clientBodyTestsEntity.getResult(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestBasicEntity().getDescription(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestBasicEntity().getPurpose()
      );
    } else {
      return new ClientBodyTestDto(
          clientBodyTestsEntity.getId(),
          clientBodyTestsEntity.getDictBodyTestId(),
          PER_USER,
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestPerUserEntity().getId(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestPerUserEntity().getName(),
          clientBodyTestsEntity.getResult(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestPerUserEntity().getDescription(),
          clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestPerUserEntity().getPurpose()
      );
    }
  }
}
