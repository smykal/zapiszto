import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import AddGoals from './AddGoals';
import GetGoals from './GetGoals';

interface ClientDetailsProps {
  client: Client;
}

const ClientGoals: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  return (
    <div>
      <AddGoals client={client} />
      <GetGoals clientId={client.id}/>
    </div>
  );
};

export default ClientGoals;
