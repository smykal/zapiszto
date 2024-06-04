package com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestBasic.repository;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestBasic.entity.DictBodyTestBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictBodyTestBasicRepository extends JpaRepository<DictBodyTestBasicEntity, Integer> {
}
