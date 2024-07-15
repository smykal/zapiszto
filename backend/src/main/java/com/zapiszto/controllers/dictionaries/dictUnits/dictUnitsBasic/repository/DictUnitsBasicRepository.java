package com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsBasic.repository;

import com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsBasic.entity.DictUnitsBasicEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictUnitsBasicRepository extends JpaRepository<DictUnitsBasicEntity, UUID> {
}
