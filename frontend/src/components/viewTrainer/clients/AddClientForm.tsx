import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import ClientsService from '../../../services/clients';
import InvitationsService from '../../../services/invitations';
import { NewClient, Invitation } from '../../../types/types';

interface AddClientFormProps {
  onClientAdded: () => void;
}

const AddClientForm: React.FC<AddClientFormProps> = ({ onClientAdded }) => {
  const [newClient, setNewClient] = useState<NewClient>({ id: '', clientName: '', userId: 0 });
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

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewClient({ ...newClient, [e.target.name]: e.target.value });
  };

  const handleInviteeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedInviteeId(parseInt(e.target.value, 10));
  };

  const handleAddClient = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const newClientWithId = { ...newClient, id: crypto.randomUUID(), userId: selectedInviteeId || 0 };
      await ClientsService.postNewClient(newClientWithId);
      setNewClient({ id: '', clientName: '', userId: 0 });
      setSelectedInviteeId(null);
      onClientAdded();
    } catch (error) {
      console.error('Error adding client:', error);
    }
  };

  return (
    <div>
      <h2>{t('buttons.add_client')}</h2>
      <form onSubmit={handleAddClient}>
        <div>
          <label>{t('clients.client_name')}:</label>
          <input
            type="text"
            name="clientName"
            value={newClient.clientName}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>{t('clients.client_assign_user')}:</label>
          <select value={selectedInviteeId || ''} onChange={handleInviteeChange}>
            <option value="">{t('clients.select_user_to_assign')}</option>
            {acceptedInvitations.map(invitation => (
              <option key={invitation.inviteeId} value={invitation.inviteeId}>
                {invitation.inviteeEmail}
              </option>
            ))}
          </select>
        </div>
        <button type="submit">{t('buttons.add')}</button>
      </form>
    </div>
  );
};

export default AddClientForm;
