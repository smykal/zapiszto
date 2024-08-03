package com.zapiszto.controllers.dictionaries.dictQuantityType.dto;

import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewDictQuantityTypeDto {
  UUID id;
  HashMap<String, String> name;
  String shortcut;
}
