package com.zapiszto.controllers.dictionaries.dictBodyTest.serializer;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dto.DictBodyTestDto;
import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.utilities.translator.Translations;

public class DictBodyTestSerializer extends Translations implements SerializerCommon {

  public static DictBodyTestDto convert(DictBodyTestEntity dictBodyTestEntity, Languages language) {

    if (dictBodyTestEntity.getDictBodyTestBasicEntity() != null) {
      var name = dictBodyTestEntity.getDictBodyTestBasicEntity().getName();
      var description = dictBodyTestEntity.getDictBodyTestBasicEntity().getDescription();
      var purpose = dictBodyTestEntity.getDictBodyTestBasicEntity().getPurpose();

      return new DictBodyTestDto(
          dictBodyTestEntity.getId(),
          BASIC,
          dictBodyTestEntity.getDictBodyTestBasicEntity()
              .getId().toString(),
          translate(name, language),
          translate(description,language),
          translate(purpose, language)
      );
    } else {
      var name = dictBodyTestEntity.getDictBodyTestPerUserEntity().getName();
      var description = dictBodyTestEntity.getDictBodyTestPerUserEntity().getDescription();
      var purpose = dictBodyTestEntity.getDictBodyTestPerUserEntity().getPurpose();

      return new DictBodyTestDto(
          dictBodyTestEntity.getId(),
          PER_USER,
          dictBodyTestEntity.getDictBodyTestPerUserEntity()
              .getId().toString(),
          translate(name, language),
          translate(description, language),
          translate(purpose, language)
      );
    }
  }
}
