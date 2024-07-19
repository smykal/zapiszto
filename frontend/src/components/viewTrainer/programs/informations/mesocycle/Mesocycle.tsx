import React, { useEffect, useState } from "react";
import { MesocycleDto } from "../../../../../types/types";
import MesocycleService from "../../../../../services/mesocycle/MesocycleService";
import { useTranslation } from "react-i18next";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Microcycle from "../microcycle/Microcycle";
import EditableCell from '../../../../../common/EditableCell';
import 'react-tabs/style/react-tabs.css';

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

  const handleSaveLabel = (id: string, newLabel: string) => {
    MesocycleService.updateMesocycleLabel(id, newLabel)
      .then(() => {
        setMesocycles((prevMesocycles) =>
          prevMesocycles.map((mesocycle) =>
            mesocycle.id === id ? { ...mesocycle, label: newLabel } : mesocycle
          )
        );
      })
      .catch(error => {
        console.error('Error updating label:', error);
        setMessage(t('mesocycle.update_error'));
      });
  };

  const handleSaveComment = (id: string, newComment: string) => {
    MesocycleService.updateMesocycleComment(id, newComment)
      .then(() => {
        setMesocycles((prevMesocycles) =>
          prevMesocycles.map((mesocycle) =>
            mesocycle.id === id ? { ...mesocycle, comments: newComment } : mesocycle
          )
        );
      })
      .catch(error => {
        console.error('Error updating comment:', error);
        setMessage(t('mesocycle.update_error'));
      });
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <Tabs>
        <TabList>
          {mesocycles.map((mesocycle) => (
            <Tab key={mesocycle.id}>{mesocycle.label || mesocycle.orderId}</Tab>
          ))}
        </TabList>
        {mesocycles.map((mesocycle) => (
          <TabPanel key={mesocycle.id}>
            <p>
              {t('table.label')}:{' '}
              <EditableCell value={mesocycle.label || ''} onSave={(newLabel) => handleSaveLabel(mesocycle.id, newLabel)} />
            </p>
            <p>{t('table.duration')}: {mesocycle.duration} tygodnie</p>
            <p>
              {t('table.comments')}:{' '}
              <EditableCell value={mesocycle.comments || ''} onSave={(newComment) => handleSaveComment(mesocycle.id, newComment)} />
            </p>
            <p>{t('table.dict_mesocycle_phase')}: {mesocycle.dictName}</p>
            <Microcycle mesocycleId={mesocycle.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Mesocycle;
