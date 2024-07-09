package com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartPerUser.repository;

import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartPerUser.entity.DictSessionPartPerUserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictSessionPartPerUserRepository extends JpaRepository<DictSessionPartPerUserEntity, UUID> {
}
