import React, { useEffect, useState } from 'react';
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Service from '../../../../../services/exercises';
import 'react-tabs/style/react-tabs.css';
import { Workbook, Client } from '../../../../../types/types';
import { withTranslation, WithTranslation } from "react-i18next";
import ShowTrainings from './ShowTrainings';



interface GetTrainingsProps extends WithTranslation {
  client: Client;
}

const IndividualTrainings: React.FC<GetTrainingsProps> = ({ client, t }) => {
  const [workbooks, setWorkbooks] = useState<Workbook[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTabIndex, setActiveTabIndex] = useState(0);

  useEffect(() => {
    if (client.userId) {
      Service.getWorkbooksByUserId(client.userId)
        .then(response => {
          setWorkbooks(response.data);
          setLoading(false);
        })
        .catch(error => {
          console.error('Error fetching workbooks:', error);
          setLoading(false);
        });
    }
  }, [client.userId]);

  useEffect(() => {
    const lastActiveTabIndex = localStorage.getItem('lastActiveTabIndex');
    if (lastActiveTabIndex !== null) {
      setActiveTabIndex(parseInt(lastActiveTabIndex));
    }
  }, []);

  const handleTabSelect = (index: number) => {
    setActiveTabIndex(index);
    localStorage.setItem('lastActiveTabIndex', index.toString());
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h3>{t('trainings')}: {client.clientName}</h3>
      <Tabs selectedIndex={activeTabIndex} onSelect={handleTabSelect}>
        <TabList>
          {workbooks.map((workbook) => (
            <Tab key={workbook.id}>{workbook.name}</Tab>
          ))}
        </TabList>
        {workbooks.map((workbook) => (
          <TabPanel key={workbook.id}>
            <ShowTrainings workbook_id={workbook.id} userId={client.userId}  />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default withTranslation("global")(IndividualTrainings);
