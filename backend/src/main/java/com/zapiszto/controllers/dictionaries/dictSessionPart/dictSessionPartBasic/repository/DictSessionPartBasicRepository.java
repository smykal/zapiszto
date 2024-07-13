package com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartBasic.repository;

import com.zapiszto.controllers.dictionaries.dictSessionPart.dictSessionPartBasic.entity.DictSessionPartBasicEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictSessionPartBasicRepository extends JpaRepository<DictSessionPartBasicEntity, UUID> {
}
