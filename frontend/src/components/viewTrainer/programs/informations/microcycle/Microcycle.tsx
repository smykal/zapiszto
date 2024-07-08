import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MicrocycleService from '../../../../../services/microcycle/MicrocycleService';
import Session from '../session/Session';
import { MicrocycleDto } from '../../../../../types/types';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';

interface MicrocycleProps {
  mesocycleId: string;
}

const Microcycle: React.FC<MicrocycleProps> = ({ mesocycleId }) => {
  const { t } = useTranslation('global');
  const [microcycles, setMicrocycles] = useState<MicrocycleDto[]>([]);
  const [message, setMessage] = useState<string>('');

  useEffect(() => {
    loadMicrocycles();
  }, [mesocycleId]);

  const loadMicrocycles = () => {
    MicrocycleService.getMicrocycles(mesocycleId)
      .then(response => {
        setMicrocycles(response.data);
      })
      .catch(error => {
        console.error('Error loading microcycles:', error);
        setMessage(t('microcycle.loading_error'));
      });
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <Tabs>
        <TabList>
          {microcycles.map((microcycle, index) => (
            <Tab key={microcycle.id}>{t('microcycle.week')}: {microcycle.orderId}</Tab>
          ))}
        </TabList>
        {microcycles.map((microcycle, index) => (
          <TabPanel key={microcycle.id}>
            <h3>{t('microcycle.details_for')} {microcycle.orderId}</h3>
            <p><strong>{t('table.dictType')}:</strong> {microcycle.dictType}</p>
            <p><strong>{t('table.dictName')}:</strong> {microcycle.dictName}</p>
            <Session microcycleId={microcycle.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Microcycle;
