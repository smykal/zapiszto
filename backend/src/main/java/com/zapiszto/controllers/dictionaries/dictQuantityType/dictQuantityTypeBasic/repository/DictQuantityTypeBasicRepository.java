package com.zapiszto.controllers.dictQuantityType.dictQuantityTypeBasic.repository;

import com.zapiszto.controllers.dictQuantityType.dictQuantityTypeBasic.entity.DictQuantityTypeBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictQuantityTypeBasicRepository extends JpaRepository<DictQuantityTypeBasicEntity, Integer> {
}
