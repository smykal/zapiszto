package com.zapiszto.controllers.invitations.invitationsStatus.repository;

import com.zapiszto.controllers.invitations.invitationsStatus.entity.InvitationsStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationsStatusRepository extends JpaRepository<InvitationsStatusEntity, Integer> {
}
