package com.zapiszto.controllers.clientBodyTest.repository;

import com.zapiszto.controllers.clientBodyTest.entity.ClientBodyTestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientBodyTestsRepository extends JpaRepository<ClientBodyTestsEntity, Integer> {
}
