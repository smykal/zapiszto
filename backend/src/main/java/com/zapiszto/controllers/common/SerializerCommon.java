package com.zapiszto.controllers.common;

import java.util.UUID;

public interface SerializerCommon {
  String PER_USER = "PER_USER";
  String BASIC = "BASIC";
  String RECIVED = "RECIVED";
  String SENT = "SENT";
  String BODY_PARAM = "BODY_PARAM";
  String BODY_TEST = "BODY_TEST";



  UUID DEFAULT_DICT_EXERCISE_ID = UUID.fromString("57ca72cb-026b-41ec-adee-0ceaec6b5992");
  UUID DEFAULT_DICT_SESSION_PART_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
  int DEFAULT_QUANTITY = 8;
  UUID DEFAULT_DICT_QUANTITY_TYPE = UUID.fromString("b9358d45-fc90-4ee6-88fb-73eda8482c6f");
  int DEFAULT_VOLUME = 50;
  UUID DEFAULLT_DICT_UNIT_ID = UUID.fromString("d7e9a7f6-b5f4-41a8-bf3a-0987654321cd");
  String DEFAULT_NOTES = "notes";
  int DEFAULT_REST_TIME = 60;
  String DEFAULT_TEMPO = "XXXX";
}
