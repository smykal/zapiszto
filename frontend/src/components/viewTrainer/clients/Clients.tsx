import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

import ClientsService from '../../../services/clients';
import { Client } from '../../../types/types';
import AddClientForm from './AddClientForm';
import ClientDetails from './ClientDetails';
const Clients = () => {
  const [clients, setClients] = useState<Client[]>([]);
  const [selectedClient, setSelectedClient] = useState<Client | null>(null);
  const [showForm, setShowForm] = useState<boolean>(false);
  const { t } = useTranslation('global');

  useEffect(() => {
    fetchClients();
  }, []);

  const fetchClients = async () => {
    try {
      const response = await ClientsService.getClients();
      if (Array.isArray(response.data)) {
        setClients(response.data);
      } else {
        console.error('Unexpected response data format:', response.data);
      }
    } catch (error) {
      console.error('Error fetching clients:', error);
    }
  };

  const handleClientClick = (client: Client) => {
    setSelectedClient(client);
    setShowForm(false); // Hide the form when a client is selected
  };

  const handleClientAdded = () => {
    fetchClients();
    setShowForm(false);
  };

  return (
    <div style={{ display: 'flex' }}>
      <div style={{ width: '20%', padding: '10px', borderRight: '1px solid #ccc' }}>
        <button onClick={() => setShowForm(!showForm)}>{t('buttons.add_client')}</button>
        <ul>
          {clients.map(client => (
            <li key={client.id} onClick={() => handleClientClick(client)}>
              {client.clientName}
            </li>
          ))}
        </ul>
      </div>
      <div style={{ width: '80%', padding: '10px' }}>
        {showForm ? (
          <AddClientForm onClientAdded={handleClientAdded} />
        ) : selectedClient ? (
          <ClientDetails client={selectedClient} />
        ) : (
          <div>{t('clients.select_client_message')}</div>
        )}
      </div>
    </div>
  );
};

export default Clients;
