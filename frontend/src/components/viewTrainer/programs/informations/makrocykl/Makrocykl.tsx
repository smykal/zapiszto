import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MacrocycleService from '../../../../../services/macrocycle/Macrocycle';
import PeriodizationService from '../../../../../services/periodization/PeriodizationService';
import { MacrocycleDto, NewMacrocycleDto, PeriodizationDto } from '../../../../../types/types';
import Mesocycle from '../mesocycle/Mesocycle';

interface MakrocyklProps {
  programId: string;
}

const Makrocykl: React.FC<MakrocyklProps> = ({ programId }) => {
  const { t } = useTranslation('global');
  const [macrocycle, setMacrocycle] = useState<MacrocycleDto | null>(null);
  const [duration, setDuration] = useState<number>(4);
  const [mesocycleDuration, setMesocycleDuration] = useState<number>(4);
  const [periodizations, setPeriodizations] = useState<PeriodizationDto[]>([]);
  const [selectedPeriodization, setSelectedPeriodization] = useState<string>('');
  const [selectedDescription, setSelectedDescription] = useState<string>('');
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

    const fetchPeriodizations = async () => {
      try {
        const response = await PeriodizationService.getDistinctPeriodizations();
        setPeriodizations(response.data);
      } catch (error) {
        console.error('Error fetching periodizations:', error);
      }
    };

    fetchMacrocycle();
    fetchPeriodizations();
  }, [programId]);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const newMacrocycle: NewMacrocycleDto = {
      id: crypto.randomUUID(),
      programId: programId,
      duration: duration,
      mesocycleDuration: mesocycleDuration,
      periodization: selectedPeriodization,
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

  const handlePeriodizationChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedName = event.target.value;
    setSelectedPeriodization(selectedName);
    const selectedPeriodization = periodizations.find(p => p.name === selectedName);
    setSelectedDescription(selectedPeriodization ? selectedPeriodization.description : '');
  };

  return (
    <div>
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
          <div>
            <label htmlFor="mesocycleDuration">{t('macrocycle.mesocycle_duration')}:</label>
            <select
              id="mesocycleDuration"
              value={mesocycleDuration}
              onChange={(e) => setMesocycleDuration(Number(e.target.value))}
            >
              {[...Array(9)].map((_, index) => (
                <option key={index + 4} value={index + 4}>
                  {index + 4}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="periodization">{t('macrocycle.periodization')}:</label>
            <select
              id="periodization"
              value={selectedPeriodization}
              onChange={handlePeriodizationChange}
            >
              <option value="">{t('select_periodization')}</option>
              {periodizations.map((periodization) => (
                <option key={periodization.name} value={periodization.name}>
                  {periodization.name}
                </option>
              ))}
            </select>
            {selectedDescription && (
              <p>{t('description')}: {selectedDescription}</p>
            )}
          </div>
          <button type="submit">{t('macrocycle.add_button')}</button>
        </form>
      )}
      {message && <p>{message}</p>}
    </div>
  );
};

export default Makrocykl;
