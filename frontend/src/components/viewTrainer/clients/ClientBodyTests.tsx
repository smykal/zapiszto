import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import AddNewBodyTest from './AddNewBodyTest';


interface ClientDetailsProps {
    client: Client
}

const ClientBodyTests: React.FC<ClientDetailsProps> =  ({client}) => {
    const { t } = useTranslation('global');
  return (
    <div>
      <AddNewBodyTest client={client} />
      <p> Body tests</p>
    </div>
  );
};
export default ClientBodyTests

