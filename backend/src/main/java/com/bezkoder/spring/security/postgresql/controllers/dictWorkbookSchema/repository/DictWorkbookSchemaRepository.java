package com.bezkoder.spring.security.postgresql.controllers.dictWorkbookSchema.repository;

import com.bezkoder.spring.security.postgresql.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictWorkbookSchemaRepository extends JpaRepository<DictWorkbookSchemaEntity, Integer> {
}
