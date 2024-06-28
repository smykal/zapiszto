import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MacrocycleService from '../../../../../services/macrocycle/Macrocycle';
import { MacrocycleDto, NewMacrocycleDto } from '../../../../../types/types';
import Mesocycle from '../mesocycle/Mesocycle';

interface MakrocyklProps {
  programId: string;
}

const Makrocykl: React.FC<MakrocyklProps> = ({ programId }) => {
  const { t } = useTranslation('global');
  const [macrocycle, setMacrocycle] = useState<MacrocycleDto | null>(null);
  const [duration, setDuration] = useState<number>(4);
  const [message, setMessage] = useState<string>('');

  useEffect(() => {
    const fetchMacrocycle = async () => {
      try {
        const response = await MacrocycleService.getMacrocycle(programId);
        setMacrocycle(response.data);
      } catch (error) {
        console.error('Error fetching macrocycle:', error);
      }
    };

    fetchMacrocycle();
  }, [programId]);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const newMacrocycle: NewMacrocycleDto = {
      id: crypto.randomUUID(),
      programId: programId,
      duration: duration,
    };

    try {
      await MacrocycleService.addMacrocycle(newMacrocycle);
      setMessage(t('macrocycle.created_successfully'));
      setMacrocycle({
        id: newMacrocycle.id,
        programId: newMacrocycle.programId,
        duration: newMacrocycle.duration,
        durationLeft: newMacrocycle.duration * 4, // Assuming duration in weeks
      });
    } catch (error) {
      console.error('Error creating macrocycle:', error);
      setMessage(t('macrocycle.creation_error'));
    }
  };

  return (
    <div>
      <h2>{t('macrocycle.title')}</h2>
      {macrocycle ? (
        <div>
          <p>{t('macrocycle.duration')}: {macrocycle.duration} months</p>
          <p>{t('macrocycle.duration_left')}: {macrocycle.durationLeft} weeks</p>
          <Mesocycle macrocycleId={macrocycle.id} initialDurationLeft={macrocycle.durationLeft} />
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="duration">{t('macrocycle.duration')}:</label>
            <select
              id="duration"
              value={duration}
              onChange={(e) => setDuration(Number(e.target.value))}
            >
              {[...Array(9)].map((_, index) => (
                <option key={index + 4} value={index + 4}>
                  {index + 4}
                </option>
              ))}
            </select>
          </div>
          <button type="submit">{t('macrocycle.add_button')}</button>
        </form>
      )}
      {message && <p>{message}</p>}
    </div>
  );
};

export default Makrocykl;
