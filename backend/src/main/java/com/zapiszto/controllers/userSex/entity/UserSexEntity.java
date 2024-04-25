package com.bezkoder.spring.security.postgresql.controllers.userSex.entity;

import com.bezkoder.spring.security.postgresql.controllers.dictSex.entity.DictSexEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_sex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSexEntity {

  @Id
  @PrimaryKeyJoinColumn
  @Column(name = "user_id")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "dict_sex_id", referencedColumnName = "id")
  private DictSexEntity dictSex;

}
