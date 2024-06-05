package com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.repository;

import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictCategoryPerUserRepository extends JpaRepository<DictCategoryPerUserEntity, Integer> {
}
