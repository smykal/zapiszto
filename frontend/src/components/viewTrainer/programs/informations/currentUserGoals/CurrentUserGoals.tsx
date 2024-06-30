import React, { useEffect, useState } from 'react';
import GoalsService from '../../../../../services/goals/GoalsService'
import { GoalDetailsDto } from '../../../../../types/types'
type CurrentUserGoalsProps = {
  clientId: string;
};

const CurrentUserGoals: React.FC<CurrentUserGoalsProps> = ({ clientId }) => {
  const [goals, setGoals] = useState<GoalDetailsDto[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    GoalsService.getGoalDetails(clientId)
      .then(response => {
        setGoals(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching goal details:', error);
        setLoading(false);
      });
  }, [clientId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <ul>
        {goals.map(goal => (
          <li key={goal.clientId}>
            <p>Body Parameter: {goal.bodyParamName}</p>
            <p>Body Test: {goal.bodyTestName}</p>
            <p>Unit: {goal.unitName}</p>
            <p>Action: {goal.action}</p>
            <p>Value: {goal.value}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CurrentUserGoals;
