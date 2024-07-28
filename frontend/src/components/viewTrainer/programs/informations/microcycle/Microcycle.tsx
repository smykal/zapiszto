import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MicrocycleService from '../../../../../services/microcycle/MicrocycleService';
import MicrocycleStats from './MicrocycleStats';
import Session from '../session/Session';
import { MicrocycleDto } from '../../../../../types/types';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import DeleteMicrocycle from './DeleteMicrocycle';

interface MicrocycleProps {
  mesocycleId: string;
}

const Microcycle: React.FC<MicrocycleProps> = ({ mesocycleId }) => {
  const { t } = useTranslation('global');
  const [microcycles, setMicrocycles] = useState<MicrocycleDto[]>([]);
  const [message, setMessage] = useState<string>('');
  const [selectedIndex, setSelectedIndex] = useState<number>(0);

  useEffect(() => {
    loadMicrocycles();
  }, [mesocycleId]);

  const loadMicrocycles = (newMicrocycleId?: string) => {
    MicrocycleService.getMicrocycles(mesocycleId)
      .then(response => {
        const sortedMicrocycles = response.data.sort((a: MicrocycleDto, b: MicrocycleDto) => a.orderId - b.orderId);
        setMicrocycles(sortedMicrocycles);
        if (newMicrocycleId) {
          const newIndex = sortedMicrocycles.findIndex((microcycle: MicrocycleDto) => microcycle.id === newMicrocycleId);
          setSelectedIndex(newIndex);
        } else {
          setSelectedIndex(0);
        }
      })
      .catch(error => {
        console.error('Error loading microcycles:', error);
        setMessage(t('microcycle.loading_error'));
      });
  };

  const handleAddMicrocycle = () => {
    MicrocycleService.addMicrocycle(mesocycleId)
      .then(response => {
        const newMicrocycle = response.data;
        loadMicrocycles(newMicrocycle.id);
      })
      .catch(error => {
        console.error('Error adding microcycle:', error);
        setMessage(t('microcycle.add_error'));
      });
  };

  const handleMicrocycleDeleted = () => {
    loadMicrocycles();
  };

  const handleShareChange = (microcycleId: string, share: boolean) => {
    MicrocycleService.updateMicrocycleShare(microcycleId, share)
      .then(() => {
        loadMicrocycles();
      })
      .catch(error => {
        console.error('Error updating microcycle share:', error);
        setMessage(t('microcycle.update_share_error'));
      });
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <Tabs selectedIndex={selectedIndex} onSelect={(index) => setSelectedIndex(index)}>
        <TabList>
          {microcycles.map((microcycle) => (
            <Tab key={microcycle.id}>{t('microcycle.week')}: {microcycle.orderId}</Tab>
          ))}
          <Tab onClick={handleAddMicrocycle}>+</Tab>
        </TabList>
        {microcycles.map((microcycle) => (
          <TabPanel key={microcycle.id}>
            <DeleteMicrocycle microcycle={microcycle} onMicrocycleDeleted={handleMicrocycleDeleted} />
            <label>
              <input
                type="checkbox"
                checked={microcycle.share}
                onChange={(e) => handleShareChange(microcycle.id, e.target.checked)}
              />
              {t('microcycle.share')}
            </label>
            <p>{t('microcycle.default_value', { week: microcycle.orderId })}</p>
            <MicrocycleStats microcycleId={microcycle.id} />
            <Session microcycleId={microcycle.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Microcycle;
