import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';

interface ClientDetailsProps {
    client: Client
}

const ClientInformations: React.FC<ClientDetailsProps> =  ({client}) => {
    const { t } = useTranslation('global');
  return (
    <div>
      <h2>{t('clients.client_details')}</h2>
      <p>ID: {client.id}</p>
      <p>{t('clients.client_name')}: {client.clientName}</p>
      <p>{t('clients.client_assigned_user')}: {client.userId === 0 ? t('clients.user_is_not_assigned') : client.userId}</p>
    </div>
  );
};
export default ClientInformations
