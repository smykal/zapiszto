package com.zapiszto.controllers.clients.repository;

import com.zapiszto.controllers.clients.entity.ClientEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<ClientEntity, Integer> {

  List<ClientEntity> getAllByTrainerId(long trainerId);

  @Query(
      nativeQuery = true, value = """
      SELECT * from clients c
          where c.id = :id
      """)
  ClientEntity getByIdUuid(@Param("id") UUID id);
}
