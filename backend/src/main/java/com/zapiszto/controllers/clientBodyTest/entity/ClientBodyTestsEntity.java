package com.zapiszto.controllers.clientBodyTest.entity;

import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "client_body_tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientBodyTestsEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
  ClientEntity clientEntity;

  @Column(name = "client_id", nullable = false)
  UUID clientId;

  @ManyToOne
  @JoinColumn(name = "dict_body_test_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictBodyTestEntity dictBodyTestEntity;

  @Column(name = "dict_body_test_id", nullable = false)
  int dictBodyTestId;

  @Column(name = "result")
  String result;

}
