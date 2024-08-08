package com.zapiszto.controllers.dictionaries.dictCategory.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.dictionaries.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictionaries.dictCategory.entity.DictCategoryEntity;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import com.zapiszto.utilities.translator.Translations;

public class DictCategorySerializer extends Translations implements SerializerCommon {

  public static DictCategoryDto convert(DictCategoryEntity dictCategoryEntity, Languages language) {

    if (dictCategoryEntity.getDictCategoryBasicEntity() != null) {
      var name = dictCategoryEntity.getDictCategoryBasicEntity()
          .getName();
      var description = dictCategoryEntity.getDictCategoryBasicEntity()
          .getDescription();

      return new DictCategoryDto(
          dictCategoryEntity.getId(),
          BASIC,
          dictCategoryEntity.getDictCategoryBasicEntity().getId(),
          translate(name, language),
          translate(description, language)
      );

    } else {
      var name = dictCategoryEntity.getDictCategoryPerUserEntity()
          .getName();
      var description = dictCategoryEntity.getDictCategoryPerUserEntity()
          .getDescription();

      return new DictCategoryDto(
          dictCategoryEntity.getId(),
          PER_USER,
          dictCategoryEntity.getDictCategoryPerUserEntity().getId(),
          translate(name, language),
          translate(description, language)
      );
    }
  }
}
