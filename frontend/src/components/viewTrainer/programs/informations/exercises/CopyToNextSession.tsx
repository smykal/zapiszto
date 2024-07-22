import React, { useState } from 'react';
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';
import { CopyParametersDto } from '../../../../../types/types';

interface CopyToNextSessionProps {
  onClose: () => void;
  sessionId: string;
}

const CopyToNextSession: React.FC<CopyToNextSessionProps> = ({ onClose, sessionId }) => {
  const [weightIncrease, setWeightIncrease] = useState<number>(0);
  const [weightIncreaseUnit, setWeightIncreaseUnit] = useState<string>('kg');
  const [repetitions, setRepetitions] = useState<number>(0);

  const handleCopy = async () => {
    const copyParametersDto: CopyParametersDto = {
      weightIncrease: weightIncrease || 0,
      weightIncreaseUnit: weightIncreaseUnit || 'kg',
      repetitions: repetitions || 0,
    };

    try {
      await ExercisesSessionService.copyExercisesToNextSession(sessionId, copyParametersDto);
      console.log('Copying to next session...');
      onClose(); // Close the modal after copying
    } catch (error) {
      console.error('Error copying to next session:', error);
    }
  };

  return (
    <div>
      <h2>Copy to Next Session</h2>
      <form>
        <div>
          <label>Weight Increase:</label>
          <input
            type="number"
            value={weightIncrease}
            onChange={(e) => setWeightIncrease(Number(e.target.value))}
          />
        </div>
        <div>
          <label>Weight Increase Unit:</label>
          <select
            value={weightIncreaseUnit}
            onChange={(e) => setWeightIncreaseUnit(e.target.value)}
          >
            <option value="kg">kg</option>
            <option value="proc">%</option>
          </select>
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

export default CopyToNextSession;
