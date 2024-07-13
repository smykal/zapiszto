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
  int DEFAULT_DICT_QUANTITY_TYPE = 10;
  int DEFAULT_VOLUME = 50;
  int DEFAULLT_DICT_UNIT_ID = 11;
  String DEFAULT_NOTES = "notes";
  int DEFAULT_REST_TIME = 60;
  String DEFAULT_TEMPO = "XXXX";
}
