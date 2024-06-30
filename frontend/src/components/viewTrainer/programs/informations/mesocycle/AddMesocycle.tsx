import React, { useState } from "react";
import { NewMesocycleDto } from "../../../../../types/types";
import MesocycleService from "../../../../../services/mesocycle/MesocycleService";
import { useTranslation } from "react-i18next";

interface AddMesocycleFormProps {
  macrocycleId: string;
  onMesocycleAdded: () => void;
}

const AddMesocycle: React.FC<AddMesocycleFormProps> = ({ macrocycleId, onMesocycleAdded }) => {
  const [duration, setDuration] = useState<number>(4);
  const [comments, setComments] = useState<string>('');
  const [message, setMessage] = useState<string>('');
  const { t } = useTranslation('global');

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const newMesocycle: NewMesocycleDto = {
      id: crypto.randomUUID(),
      macrocycleId: macrocycleId,
      duration: duration,
      comments: comments,
    };

    try {
      await MesocycleService.addMesocycle(newMesocycle);
      setMessage(t('mesocycle.created_successfully'));
      setComments(''); // Clear the comments field
      onMesocycleAdded(); // Callback to parent component to reload mesocycles
    } catch (error) {
      console.error('Error creating mesocycle:', error);
      setMessage(t('mesocycle.creation_error'));
    }
  };

  return (
    <div>
      <h2>{t('mesocycle.add_title')}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="duration">{t('mesocycle.duration')}:</label>
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
          <label htmlFor="comments">{t('mesocycle.comments')}:</label>
          <input
            type="text"
            id="comments"
            value={comments}
            onChange={(e) => setComments(e.target.value)}
          />
        </div>
        <button type="submit">{t('mesocycle.add_button')}</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AddMesocycle;
