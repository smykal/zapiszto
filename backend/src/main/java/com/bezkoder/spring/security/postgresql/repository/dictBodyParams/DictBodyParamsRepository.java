package com.bezkoder.spring.security.postgresql.repository.dictBodyParams;

import com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.entity.DictBodyParamsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictBodyParamsRepository extends JpaRepository<DictBodyParamsEntity, Integer> {
}
