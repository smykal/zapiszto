import React, { useEffect, useState } from "react";
import { MesocycleDto } from "../../../../../types/types";
import MesocycleService from "../../../../../services/mesocycle/MesocycleService";
import { useTranslation } from "react-i18next";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Microcycle from "../microcycle/Microcycle";

interface MesocycleProps {
  macrocycleId: string;
  initialDurationLeft: number;
}

const Mesocycle: React.FC<MesocycleProps> = ({ macrocycleId, initialDurationLeft }) => {
  const [mesocycles, setMesocycles] = useState<MesocycleDto[]>([]);
  const [message, setMessage] = useState<string>('');
  const { t } = useTranslation('global');

  useEffect(() => {
    loadMesocycles();
  }, [macrocycleId]);

  const loadMesocycles = () => {
    MesocycleService.getMesocycles(macrocycleId)
      .then(response => {
        setMesocycles(response.data);
      })
      .catch(error => {
        console.error('Error loading mesocycles:', error);
        setMessage(t('mesocycle.loading_error'));
      });
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <strong>{t('mesocycle.select_mesocycle')}</strong>
      <Tabs>
        <TabList>
          {mesocycles.map((mesocycle) => (
            <Tab key={mesocycle.id}>{mesocycle.orderId}</Tab>
          ))}
        </TabList>
        {mesocycles.map((mesocycle) => (
          <TabPanel key={mesocycle.id}>
            <h3>{t('mesocycle.details')}</h3>
            <p>{t('table.duration')}: {mesocycle.duration}</p>
            <p>{t('table.comments')}: {mesocycle.comments}</p>
            <p>{t('table.dictType')}: {mesocycle.dictType}</p>
            <p>{t('table.dictId')}: {mesocycle.dictId}</p>
            <p>{t('table.dictName')}: {mesocycle.dictName}</p>
            <Microcycle mesocycleId={mesocycle.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Mesocycle;
