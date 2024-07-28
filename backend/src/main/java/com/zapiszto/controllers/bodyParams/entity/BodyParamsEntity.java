package com.zapiszto.controllers.bodyParams.entity;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import com.zapiszto.utilities.encryption.Encrypt;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "body_params")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BodyParamsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne
  @JoinColumn(name = "dict_body_params_id", referencedColumnName = "id")
  DictBodyParamsEntity dictBodyParams;

  @Column(name = "value")
  @Convert(converter = Encrypt.class)
  String value;

  @Column(name = "user_id")
  Long userId;

  @Column(name = "insert_date")
  ZonedDateTime insert_date;

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
  ClientEntity clientEntity;

  @Column(name = "client_id")
  UUID clientId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;
}
