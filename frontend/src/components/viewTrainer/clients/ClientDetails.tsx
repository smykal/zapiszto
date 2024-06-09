import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import ClientInformations from './informations/ClientInformations';
import ClientBody from './body/ClientBody';
import ClientGoals from './goals/ClientGoals';
import Collapsible from 'react-collapsible';

interface ClientDetailsProps {
  client: Client;
}

const ClientDetails: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("client.informations")} open={false}>
        <ClientInformations client={client} />
      </Collapsible>

      <Collapsible trigger={t("client.body")} open={false}>
        <ClientBody client={client} />
      </Collapsible>

      <Collapsible trigger={t("client.goals")} open={false}>
        <ClientGoals client={client} />
      </Collapsible>
    </div>
  );
};

export default ClientDetails;
