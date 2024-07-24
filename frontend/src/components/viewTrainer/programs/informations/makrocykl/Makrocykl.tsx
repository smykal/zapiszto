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
  const [mesocycleDuration] = useState<number>(4); // Fixed value 4
  const [periodizations, setPeriodizations] = useState<PeriodizationDto[]>([]);
  const [selectedPeriodization, setSelectedPeriodization] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [sessionsForMicrocycle, setSessionsForMicrocycle] = useState<number>(3);
  const [durationSession, setDurationSession] = useState<number>(60);
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
        const response = await PeriodizationService.getPeriodizations();
        setPeriodizations(response.data);
      } catch (error) {
        console.error('Error fetching periodizations:', error);
      }
    };

    fetchMacrocycle();
    fetchPeriodizations();
  }, [programId]);

  const handlePeriodizationChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedName = event.target.value;
    setSelectedPeriodization(selectedName);
    const selected = periodizations.find(p => p.name === selectedName);
    setDescription(selected ? selected.description : '');
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const newMacrocycle: NewMacrocycleDto = {
      id: crypto.randomUUID(),
      programId: programId,
      duration: 4, // Fixed value 4
      mesocycleDuration: 4, // Fixed value 4
      periodization: selectedPeriodization,
      durationSession: durationSession,
      sessionsForMicrocycle: sessionsForMicrocycle,
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
      {macrocycle ? (
        <div>
          <p>{t('macrocycle.duration')}: {macrocycle.duration} months</p>
          <p>{t('macrocycle.duration_left')}: {macrocycle.durationLeft} weeks</p>
          <Mesocycle macrocycleId={macrocycle.id} initialDurationLeft={macrocycle.durationLeft} />
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="sessionsForMicrocycle">{t('macrocycle.sessions_per_microcycle')}</label>
            <select
              id="sessionsForMicrocycle"
              value={sessionsForMicrocycle}
              onChange={(e) => setSessionsForMicrocycle(Number(e.target.value))}
            >
              {[...Array(6)].map((_, index) => (
                <option key={index + 1} value={index + 1}>
                  {index + 1}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="durationSession">{t('macrocycle.session_duration')}</label>
            <select
              id="durationSession"
              value={durationSession}
              onChange={(e) => setDurationSession(Number(e.target.value))}
            >
              {[45, 60, 75, 90, 105, 120].map((value) => (
                <option key={value} value={value}>
                  {value} minutes
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="periodization">{t('macrocycle.periodization')}</label>
            <select
              id="periodization"
              value={selectedPeriodization}
              onChange={handlePeriodizationChange}
              required
            >
              <option value="" disabled>{t('macrocycle.select_periodization')}</option>
              {periodizations.map((periodization) => (
                <option key={periodization.name} value={periodization.name}>
                  {periodization.name}
                </option>
              ))}
            </select>
            {description && <p>{t('macrocycle.description')}: {description}</p>}
          </div>
          <button type="submit">{t('macrocycle.add_button')}</button>
        </form>
      )}
      {message && <p>{message}</p>}
    </div>
  );
};

export default Makrocykl;
