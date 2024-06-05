package com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestPerUser.repository;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestPerUser.entity.DictBodyTestPerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictBodyTestPerUserRepository extends JpaRepository<DictBodyTestPerUserEntity, Integer> {
}
