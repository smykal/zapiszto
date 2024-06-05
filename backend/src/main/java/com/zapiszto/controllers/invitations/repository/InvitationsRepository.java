package com.zapiszto.controllers.invitations.repository;

import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationsRepository extends JpaRepository<InvitationsEntity, Integer> {
  InvitationsEntity getById(UUID id);
}
