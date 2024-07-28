import React, { useState } from 'react';
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';
import { CopyParametersDto } from '../../../../../types/types';

interface CopyToNextDaySessionProps {
  onClose: () => void;
  sessionId: string;
}

const CopyToNextDaySession: React.FC<CopyToNextDaySessionProps> = ({ onClose, sessionId }) => {
  const [weightIncrease, setWeightIncrease] = useState<number>(0);
  const [weightIncreaseUnit, setWeightIncreaseUnit] = useState<string>('%');
  const [repetitions, setRepetitions] = useState<number>(0);

  const handleCopy = async () => {
    const copyParametersDto: CopyParametersDto = {
      weightIncrease: weightIncrease || 0,
      weightIncreaseUnit: weightIncreaseUnit || '%',
      repetitions: repetitions || 0,
    };

    try {
      await ExercisesSessionService.copyExercisesToNextDaySession(sessionId, copyParametersDto);
      console.log('Copying to next day session...');
      onClose(); // Close the modal after copying
    } catch (error) {
      console.error('Error copying to next day session:', error);
    }
  };

  return (
    <div>
      <form>
        <div>
          <label>Weight Increase Unit:</label>
          <div>
            <label>
              <input
                type="radio"
                value="kg"
                checked={weightIncreaseUnit === 'kg'}
                onChange={(e) => setWeightIncreaseUnit(e.target.value)}
              />
              kg
            </label>
            <label>
              <input
                type="radio"
                value="%"
                checked={weightIncreaseUnit === '%'}
                onChange={(e) => setWeightIncreaseUnit(e.target.value)}
              />
              %
            </label>
          </div>
          <div>
            <label>Weight Increase:</label>
            <input
              type="number"
              value={weightIncrease}
              onChange={(e) => setWeightIncrease(Number(e.target.value))}
            />
          </div>
        </div>
        <div>
          <label>Repetitions:</label>
          <input
            type="number"
            value={repetitions}
            onChange={(e) => setRepetitions(Number(e.target.value))}
          />
        </div>
        <button type="button" onClick={handleCopy}>Copy</button>
        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default CopyToNextDaySession;
