import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import ClientBodyParams from './ClientBodyParams';
import ClientBodyTests from './ClientBodyTests';


interface ClientDetailsProps {
    client: Client
}

const ClientBody: React.FC<ClientDetailsProps> =  ({client}) => {
    const { t } = useTranslation('global');
  return (
    <div>
      <Tabs>
        <TabList>
            <Tab key="tests">Tests</Tab>
            <Tab key="body_params">Body Params</Tab>
        </TabList>
        <TabPanel key="tests"> <ClientBodyTests client={client} /></TabPanel>
        <TabPanel key="body_params"> <ClientBodyParams client={client} /> </TabPanel>
      </Tabs>
    </div>
  );
};
export default ClientBody

