import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MicrocycleService from '../../../../../services/microcycle/MicrocycleService';
import MicrocycleStats from './MicrocycleStats';
import Session from '../session/Session';
import { MicrocycleDto } from '../../../../../types/types';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import DeleteMicrocycle from './DeleteMicrocycle'; // Importujemy nowy komponent

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
        loadMicrocycles(newMicrocycle.id);  // Ponowne załadowanie mikrocykli po dodaniu nowego mikrocyklu i przejście do nowej zakładki
      })
      .catch(error => {
        console.error('Error adding microcycle:', error);
        setMessage(t('microcycle.add_error'));
      });
  };

  const handleMicrocycleDeleted = () => {
    loadMicrocycles();  // Ponowne załadowanie mikrocykli po usunięciu mikrocyklu
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
            <DeleteMicrocycle microcycle={microcycle} onMicrocycleDeleted={handleMicrocycleDeleted} /> {/* Nowy przycisk do usuwania */}
            <p>tutaj pobrać dla każdego kolejnego tygodnia domyślną wartość z dict_mesocycle_phase</p>
            <MicrocycleStats microcycleId={microcycle.id} />
            <Session microcycleId={microcycle.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Microcycle;
