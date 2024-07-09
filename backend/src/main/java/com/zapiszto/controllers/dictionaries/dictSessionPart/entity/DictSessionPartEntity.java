package com.zapiszto.controllers.dictionaries.dictSessionPart.entity;

import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartBasic.entity.DictSessionPartBasicEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartPerUser.entity.DictSessionPartPerUserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "dict_session_part")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictSessionPartEntity {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "dict_session_part_basic_id", nullable = false)
  private DictSessionPartBasicEntity dictSessionPartBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_session_part_per_user_id", nullable = false)
  private DictSessionPartPerUserEntity dictSessionPartPerUserEntity;
}
