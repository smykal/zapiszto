package com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestPerUser.entity;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
@Table(name = "dict_body_test_per_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictBodyTestPerUserEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "name")
  HashMap<String, String> name;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "description")
  HashMap<String, String> description;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "purpose")
  HashMap<String, String> purpose;

  @Column(name = "user_id")
  Long user_id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  User user;

  @OneToMany(mappedBy = "dictBodyTestPerUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<DictBodyTestEntity> dictBodyTests = new HashSet<>();
}
