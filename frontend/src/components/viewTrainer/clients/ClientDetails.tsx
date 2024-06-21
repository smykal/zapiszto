import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import ClientInformations from './informations/ClientInformations';
import ClientBody from './body/ClientBody';
import ClientGoals from './goals/ClientGoals';
import ClientTrainings from './trainings/ClientTrainings';
import DeleteClient from './informations/DeleteClient';// Import komponentu usuwania klienta
import Collapsible from 'react-collapsible';

interface ClientDetailsProps {
  client: Client;
  onClientDeleted: () => void; // Dodajemy nową właściwość
}

const ClientDetails: React.FC<ClientDetailsProps> = ({ client, onClientDeleted }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("clients.informations")} open={true}>
        <ClientInformations client={client} />
        <DeleteClient client={client} onClientDeleted={onClientDeleted} /> {/* Dodajemy komponent DeleteClient */}
      </Collapsible>

      <Collapsible trigger={t("clients.body")} open={false}>
        <ClientBody client={client} />
      </Collapsible>

      <Collapsible trigger={t("clients.goals")} open={false}>
        <ClientGoals client={client} />
      </Collapsible>

      <Collapsible trigger={t("clients.trainings")} open={false}>
        <ClientTrainings client={client} />
      </Collapsible>
    </div>
  );
};

export default ClientDetails;
