import React , { useState, useEffect }from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import UpdateClientUser from './UpdateClientUser';

interface ClientDetailsProps {
  client: Client;
}

const ClientInformations: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  const [userId, setUserId] = useState(client.userId);

  useEffect(() => {
    setUserId(client.userId);
  }, [client.userId]);

  return (
    <div>
      <h2>{t('clients.client_details')}</h2>
      <p>ID: {client.id}</p>
      <p>{t('clients.client_name')}: {client.clientName}</p>
      <p>
        {t('clients.client_assigned_user')}: {userId === 0 ? t('clients.user_is_not_assigned') : userId}
      </p>
      {userId === 0 && <UpdateClientUser client={client} />}
    </div>
  );
};

export default ClientInformations;
