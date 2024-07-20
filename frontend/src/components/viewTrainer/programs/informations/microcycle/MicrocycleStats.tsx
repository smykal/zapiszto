import React, { useEffect, useState } from 'react';
import MicrocycleService from '../../../../../services/microcycle/MicrocycleService';
import { MicrocycleStatsDto } from '../../../../../types/types';

interface MicrocycleStatsProps {
  microcycleId: string;
}

const MicrocycleStats: React.FC<MicrocycleStatsProps> = ({ microcycleId }) => {
  const [stats, setStats] = useState<MicrocycleStatsDto[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadStats();
  }, [microcycleId]);

  const loadStats = () => {
    MicrocycleService.getMicrocycleStats(microcycleId)
      .then(response => {
        setStats(response.data);
      })
      .catch(error => {
        console.error('Error loading microcycle stats:', error);
        setError('Error loading microcycle stats');
      });
  };

  return (
    <div>
      {error && <p>{error}</p>}
      <table>
        <thead>
          <tr>
            <th>Category</th>
            <th>Repetitions</th>
            <th>Sets</th>
          </tr>
        </thead>
        <tbody>
          {stats.map((stat, index) => (
            <tr key={index}>
              <td>{stat.category}</td>
              <td>{stat.repetitions}</td>
              <td>{stat.sets}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default MicrocycleStats;
