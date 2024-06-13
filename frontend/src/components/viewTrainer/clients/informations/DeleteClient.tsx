import React from 'react';
import ClientsService from '../../../../services/clients';
import { Client } from '../../../../types/types';
import { useTranslation } from 'react-i18next';

interface DeleteClientProps {
  client: Client;
  onClientDeleted: () => void;
}

const DeleteClient: React.FC<DeleteClientProps> = ({ client, onClientDeleted }) => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    const confirmMessage = `${t('clients.confirm_delete')} ${client.clientName}`;

    if (window.confirm(confirmMessage)) {
      try {
        await ClientsService.deleteClient(client.id);
        onClientDeleted();
        console.log('Client deleted successfully');
      } catch (error) {
        console.error('Error deleting client:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('buttons.delete_client')}
    </button>
  );
};

export default DeleteClient;
