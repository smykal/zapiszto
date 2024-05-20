package com.zapiszto.controllers.invitations.dictInvitationsStatus.repository;

import com.zapiszto.controllers.invitations.dictInvitationsStatus.entity.DictInvitationsStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictInvitationsStatusRepository extends JpaRepository<DictInvitationsStatusEntity, Integer> {
}
