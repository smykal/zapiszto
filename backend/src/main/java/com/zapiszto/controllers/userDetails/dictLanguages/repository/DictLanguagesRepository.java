package com.zapiszto.controllers.userDetails.dictLanguages.repository;

import com.zapiszto.controllers.userDetails.dictLanguages.entity.DictLanguagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictLanguagesRepository extends JpaRepository<DictLanguagesEntity, Integer> {

  DictLanguagesEntity getByCode(String code);
}
