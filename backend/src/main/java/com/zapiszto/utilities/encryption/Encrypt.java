package com.zapiszto.utilities.encryption;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class Encrypt implements AttributeConverter<String, String> {
  @Autowired
  EncryptionUtil encryptionUtil;

  @Override
  public String convertToDatabaseColumn(String s) {
    return encryptionUtil.encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    return encryptionUtil.decrypt(s);
  }
}
