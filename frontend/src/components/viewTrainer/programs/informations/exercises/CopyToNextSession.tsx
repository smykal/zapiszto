import React from 'react';
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';

interface CopyToNextSessionProps {
  onClose: () => void;
  sessionId: string;
}

const CopyToNextSession: React.FC<CopyToNextSessionProps> = ({ onClose, sessionId }) => {
  const handleCopy = async () => {
    try {
      await ExercisesSessionService.copyExercisesToNextSession(sessionId);
      console.log('Copying to next session...');
      onClose(); // Close the modal after copying
    } catch (error) {
      console.error('Error copying to next session:', error);
    }
  };

  return (
    <div>
      <p>Here you can add options for copying to the next session.</p>
      <button onClick={handleCopy}>Copy</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CopyToNextSession;
