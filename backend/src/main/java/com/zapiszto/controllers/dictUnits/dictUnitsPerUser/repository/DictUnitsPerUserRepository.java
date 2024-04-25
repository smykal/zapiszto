package com.zapiszto.controllers.dictUnits.dictUnitsPerUser.repository;

import com.zapiszto.controllers.dictUnits.dictUnitsPerUser.entity.DictUnitsPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictUnitsPerUserRepository extends JpaRepository<DictUnitsPerUserEntity, Integer> {
}
