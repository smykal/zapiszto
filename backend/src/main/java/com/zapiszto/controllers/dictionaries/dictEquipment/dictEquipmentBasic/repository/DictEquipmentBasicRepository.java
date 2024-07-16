package com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentBasic.repository;

import com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentBasic.entity.DictEquipmentBasicEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DictEquipmentBasicRepository extends JpaRepository<DictEquipmentBasicEntity, UUID> {
}
