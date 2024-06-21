package com.zapiszto.controllers.clients.repository;

import com.zapiszto.controllers.clients.entity.ClientEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientsRepository extends JpaRepository<ClientEntity, Integer> {

  List<ClientEntity> getAllByTrainerId(long trainerId);

  @Query(
      nativeQuery = true, value = """
      SELECT * from clients c
          where c.id = :id
      """)
  ClientEntity getByIdUuid(@Param("id") UUID id);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = """
      DELETE FROM public.clients
      WHERE id=:clientId
      """)
  void deleteByClientId(@Param("clientId") UUID clientId);
}
