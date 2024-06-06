import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import ClientInformations from './ClientInformations';
import ClientBody from './ClientBody';
import ClientGoals from './goals/ClientGoals';

interface ClientDetailsProps {
  client: Client;
}

const ClientDetails: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  return (
    <div>
      <ClientInformations client={client} />
      <ClientBody client={client} />
      <ClientGoals client={client} />
    </div>
  );
};

export default ClientDetails;
