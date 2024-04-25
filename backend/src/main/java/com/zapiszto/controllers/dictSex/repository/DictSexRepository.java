package com.zapiszto.controllers.dictSex.repository;

import com.zapiszto.controllers.dictSex.entity.DictSexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictSexRepository extends JpaRepository<DictSexEntity, Integer> {
}
