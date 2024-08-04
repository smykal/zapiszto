package com.zapiszto.controllers.dictionaries.dictUnits.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.controllers.dictionaries.dictUnits.dto.DictUnitsDto;
import com.zapiszto.controllers.dictionaries.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.utilities.translator.Translations;

public class DictUnitsSerializer extends Translations implements SerializerCommon {

  public static DictUnitsDto convert(DictUnitsEntity dictUnitsEntity, Languages language) {

    if(dictUnitsEntity.getDictUnitsBasicEntity() != null) {
      var name = dictUnitsEntity.getDictUnitsBasicEntity()
          .getName();
      var shortcut = dictUnitsEntity.getDictUnitsBasicEntity()
          .getShortcut();
      
      return new DictUnitsDto(
          dictUnitsEntity.getId(),
          BASIC,
          dictUnitsEntity.getDictUnitsBasicEntity().getId(),
          translate(name, language),
          translate(shortcut, language)
      );
    } else {
      var name = dictUnitsEntity.getDictUnitsPerUserEntity()
          .getName();
      var shortcut = dictUnitsEntity.getDictUnitsPerUserEntity()
          .getShortcut();
      
      return new DictUnitsDto(
          dictUnitsEntity.getId(),
          PER_USER,
          dictUnitsEntity.getDictUnitsPerUserEntity().getId(),
          translate(name, language),
          translate(shortcut, language)
      );
    }
  }
}
