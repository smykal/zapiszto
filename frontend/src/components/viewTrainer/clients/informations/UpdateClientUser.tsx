import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import InvitationsService from '../../../../services/invitations';
import ClientsService from '../../../../services/clients';
import { Client, Invitation } from '../../../../types/types';

interface UpdateClientUserProps {
  client: Client;
}

const UpdateClientUser: React.FC<UpdateClientUserProps> = ({ client }) => {
  const [acceptedInvitations, setAcceptedInvitations] = useState<Invitation[]>([]);
  const [selectedInviteeId, setSelectedInviteeId] = useState<number | null>(null);
  const { t } = useTranslation('global');

  useEffect(() => {
    fetchAcceptedInvitations();
  }, []);

  const fetchAcceptedInvitations = async () => {
    try {
      const response = await InvitationsService.getAcceptedInvitations();
      if (Array.isArray(response.data)) {
        setAcceptedInvitations(response.data);
      } else {
        console.error('Unexpected response data format:', response.data);
      }
    } catch (error) {
      console.error('Error fetching accepted invitations:', error);
    }
  };

  const handleInviteeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedInviteeId(parseInt(e.target.value, 10));
  };

  const handleUpdateUser = async (e: React.FormEvent) => {
    e.preventDefault();
    if (selectedInviteeId === null) {
      console.error('No invitee selected');
      return;
    }
    try {
      await ClientsService.updateClientUser(client.id, selectedInviteeId, client.clientName);
      console.log('User updated successfully');
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  return (
    <div>
      <form onSubmit={handleUpdateUser} className="form-row">
        <div className="form-group">
          <label>{t('clients.select_user_to_assign')}:</label>
        </div>
        <div className="form-group">
          <select value={selectedInviteeId || ''} onChange={handleInviteeChange}>
            <option value="">{t('clients.select_user_to_assign')}</option>
            {acceptedInvitations.map(invitation => (
              <option key={invitation.inviteeId} value={invitation.inviteeId}>
                {invitation.inviteeEmail}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <button type="submit" className="myButton">{t('buttons.update')}</button>
        </div>
      </form>
    </div>
  );
};

export default UpdateClientUser;
