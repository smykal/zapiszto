package com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentPerUser.repository;

import com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentPerUser.entity.DictEquipmentPerUserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictEquipmentPerUserRepository extends JpaRepository<DictEquipmentPerUserEntity, UUID> {
}
