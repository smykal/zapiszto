package com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.repository;

import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictQuantityTypePerUserRepository extends JpaRepository<DictQuantityTypePerUserEntity, Integer> {
}
