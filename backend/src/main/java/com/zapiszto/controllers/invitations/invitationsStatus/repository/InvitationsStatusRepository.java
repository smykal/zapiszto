package com.zapiszto.controllers.invitations.invitationsStatus.repository;

import com.zapiszto.controllers.invitations.invitationsStatus.entity.InvitationsStatusEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationsStatusRepository extends JpaRepository<InvitationsStatusEntity, Integer> {

  @Query(nativeQuery = true, value = """
      select
      	status.*
      	from invitations_status status
      	left join invitations i on i.id = status.invitations_id\s
      where
      	(i.inviter = :userId
      		or i.invitee = :userId)
      """)
  List<InvitationsStatusEntity> getInvitations(@Param("userId") Long userId);

  @Query(nativeQuery = true, value = """
      select dis."name"  from invitations_status is2
      		left join dict_invitations_status dis on dis.id = is2.dict_invitations_status_id
      where invitations_id = :invitationId
      order by is2.insert_date desc
      limit 1
      """)
  String getLastStatus(@Param("invitationId") UUID invitationId);
}
