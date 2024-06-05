package com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsPerUser.repository;

import com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsPerUser.entity.DictUnitsPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictUnitsPerUserRepository extends JpaRepository<DictUnitsPerUserEntity, Integer> {
}
