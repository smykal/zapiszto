package com.zapiszto.controllers.userDetails.entity;

import com.zapiszto.controllers.dictionaries.dictLanguages.entity.DictLanguagesEntity;
import com.zapiszto.controllers.dictionaries.dictSex.entity.DictSexEntity;
import com.zapiszto.controllers.account.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsEntity {

  @Id
  @Column(name = "user_id")
  private long userId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "dict_language_id", referencedColumnName = "id")
  private DictLanguagesEntity dictLanguagesEntity;

  @Column(name = "birthdate")
  private ZonedDateTime birthdate;

  @ManyToOne
  @JoinColumn(name = "dict_sex_id", referencedColumnName = "id")
  private DictSexEntity dictSex;

}
