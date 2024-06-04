package com.zapiszto.controllers.dictionaries.dictWorkbookSchema.repository;

import com.zapiszto.controllers.dictionaries.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictWorkbookSchemaRepository extends JpaRepository<DictWorkbookSchemaEntity, Integer> {
}
