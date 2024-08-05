package com.zapiszto.controllers.clientBodyTest.serializer;

import com.zapiszto.controllers.clientBodyTest.dto.ClientBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.utilities.translator.Translations;

public class ClientBodyTestSerializer extends Translations implements SerializerCommon {

  public static ClientBodyTestDto convert(ClientBodyTestsEntity clientBodyTestsEntity, Languages language) {

    if (clientBodyTestsEntity.getDictBodyTestEntity()
        .getDictBodyTestBasicEntity() != null) {
      var dict = clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestBasicEntity();
      return new ClientBodyTestDto(
          clientBodyTestsEntity.getId(),
          clientBodyTestsEntity.getDictBodyTestId(),
          BASIC,
          dict.getId(),
          translate(dict.getName() ,language) ,
          clientBodyTestsEntity.getResult(),
          translate(dict.getDescription() ,language),
          translate(dict.getPurpose() ,language)
      );
    } else {
      var dict = clientBodyTestsEntity.getDictBodyTestEntity().getDictBodyTestPerUserEntity();
      return new ClientBodyTestDto(
          clientBodyTestsEntity.getId(),
          clientBodyTestsEntity.getDictBodyTestId(),
          PER_USER,
          dict.getId(),
          translate(dict.getName(), language),
          clientBodyTestsEntity.getResult(),
          translate(dict.getDescription(), language),
          translate(dict.getPurpose(), language)
      );
    }
  }
}
