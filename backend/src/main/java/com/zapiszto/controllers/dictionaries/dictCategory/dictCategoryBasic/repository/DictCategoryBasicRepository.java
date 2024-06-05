package com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryBasic.repository;

import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryBasic.entity.DictCategoryBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictCategoryBasicRepository extends JpaRepository<DictCategoryBasicEntity, Integer> {
}
