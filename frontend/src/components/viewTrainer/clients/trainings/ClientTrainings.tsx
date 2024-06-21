import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import PersonalTrainings from './personalTrainings/PersonalTrainings';
import IndividualTrainings from './individualTrainings/IndividualTrainings';

interface ClientDetailsProps {
    client: Client
}

const ClientTrainings: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    return (
        <div>
            <Tabs>
                <TabList>
                    <Tab key="individual_trainings">{t('clients.individual_trainings')}</Tab>
                    <Tab key="personal_trainings">{t('clients.personal_trainings')}</Tab>
                </TabList>
                <TabPanel key="individual_trainings">
                    <IndividualTrainings client={client} />
                </TabPanel>
                <TabPanel key="personal_trainings">
                    <PersonalTrainings client={client} />
                </TabPanel>
            </Tabs>
        </div>
    );
};

export default ClientTrainings;