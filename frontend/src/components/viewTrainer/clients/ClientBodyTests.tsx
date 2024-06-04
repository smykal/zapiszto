import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import AddNewBodyTest from './AddNewBodyTest';
import AddClientBodyTest from './AddClientBodyTest';
import GetClientBodyTest from './GetClientBodyTest';



interface ClientDetailsProps {
  client: Client
}

const ClientBodyTests: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  return (
    <div>
      <GetClientBodyTest client={client} />
      <AddClientBodyTest client={client} />
      <AddNewBodyTest client={client} />
    </div>
  );
};
export default ClientBodyTests

