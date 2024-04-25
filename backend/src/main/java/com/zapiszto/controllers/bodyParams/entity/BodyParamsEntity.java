package com.zapiszto.controllers.bodyParams.entity;

import com.zapiszto.controllers.dictBodyParams.entity.DictBodyParamsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "body_params")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyParamsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "dict_body_params_id", referencedColumnName = "id")
  private DictBodyParamsEntity dictBodyParams;

  @Column(name = "value")
  private String value;

  @Column(name = "user_id")
  private int userId;

  @Column(name = "insert_date")
  private ZonedDateTime insert_date;
}
