package com.zapiszto.controllers.common;

import java.util.UUID;

public interface SerializerCommon {
  String PER_USER = "PER_USER";
  String BASIC = "BASIC";
  String RECIVED = "RECIVED";
  String SENT = "SENT";
  String BODY_PARAM = "BODY_PARAM";
  String BODY_TEST = "BODY_TEST";

  UUID DEFAULT_DICT_EXERCISE_ID = UUID.fromString("25c0824a-a7ef-4cf4-91b6-5e84ad9331a5");
  int DEFAULT_QUANTITY = 100;
  int DEFAULT_DICT_QUANTITY_TYPE = 1;
  int DEFAULT_VOLUME = 10;
  int DEFAULLT_DICT_UNIT_ID = 1;
  String DEFAULT_NOTES = "notes";
  int DEFAULT_REST_TIME = 60;
  String DEFAULT_TEMPO = "XXXX";
}
