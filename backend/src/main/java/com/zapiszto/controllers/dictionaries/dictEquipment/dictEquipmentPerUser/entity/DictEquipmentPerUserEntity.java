package com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentPerUser.entity;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictLanguages.options.Languages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "dict_equipment_per_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictEquipmentPerUserEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  UUID id;

  @Column(name = "user_id")
  Long user_id;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "name")
  HashMap<String, String> name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  User user;

  @OneToOne(mappedBy = "dictEquipmentPerUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  DictEquipmentEntity dictEquipment;
}
