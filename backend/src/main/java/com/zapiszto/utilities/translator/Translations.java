package com.zapiszto.utilities.translator;

import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import java.util.Map;
import java.util.Optional;


public class Translations {

  public static String translate(Map<String, String> value, Languages language) {
    String result = value.get(language.name());

    if (result == null) {
      result = value.get("en");
    }

    if (result == null) {
      result = value.get("pl");
    }

    if (result == null) {
      Optional<String> anyValue = value.values()
          .stream()
          .findFirst();
      result = anyValue.orElse(null);
    }

    return result;
  }
}
