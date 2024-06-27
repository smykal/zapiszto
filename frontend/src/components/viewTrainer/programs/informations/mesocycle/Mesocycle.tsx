import React, { useEffect, useState } from "react";
import { MesocycleDto, NewMesocycleDto } from "../../../../../types/types";
import MesocycleService from "../../../../../services/mesocycle/MesocycleService";
import { useTranslation } from "react-i18next";

interface MesocycleProps {
  macrocycleId: string;
  initialDurationLeft: number;
}

const Mesocycle: React.FC<MesocycleProps> = ({ macrocycleId, initialDurationLeft }) => {
  const [mesocycles, setMesocycles] = useState<MesocycleDto[]>([]);
  const [duration, setDuration] = useState<number>(4);
  const [comments, setComments] = useState<string>('');
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
      });
  };

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
      loadMesocycles(); // Reload mesocycles after adding new one
    } catch (error) {
      console.error('Error creating mesocycle:', error);
      setMessage(t('mesocycle.creation_error'));
    }
  };

  return (
    <div>
      <h2>{t('mesocycle.title')}</h2>
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
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>{t("table.id")}</th>
            <th>{t("table.duration")}</th>
            <th>{t("table.comments")}</th>
            <th>{t("table.orderId")}</th>
          </tr>
        </thead>
        <tbody>
          {mesocycles.map((row) => (
            <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
              <td>{row.id}</td>
              <td>{row.duration}</td>
              <td>{row.comments}</td>
              <td>{row.orderId}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Mesocycle;
