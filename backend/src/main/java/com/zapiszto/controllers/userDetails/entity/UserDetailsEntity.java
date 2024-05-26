package com.zapiszto.controllers.userDetails.entity;

import com.zapiszto.controllers.dictSex.entity.DictSexEntity;
import com.zapiszto.controllers.userDetails.dictLanguages.entity.DictLanguagesEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @ManyToOne
  @JoinColumn(name = "dict_language_id", referencedColumnName = "id")
  private DictLanguagesEntity dictLanguagesEntity;


}