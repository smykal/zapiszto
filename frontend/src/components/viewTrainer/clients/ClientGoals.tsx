import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../types/types';
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import ClientBodyParams from './ClientBodyParams';
import ClientBodyTests from './ClientBodyTests';


interface ClientDetailsProps {
    client: Client
}

const ClientGoals: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    return (
        <div>
            <p>cele</p>
            <p>{client.clientName}</p>
            <p>{client.id}</p>
            <p>{client.userId}</p>

        </div>
    );
};
export default ClientGoals

