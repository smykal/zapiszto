import React from 'react';
import { Client } from '../../../../../types/types';

interface PersonalTrainingsProps {
    client: Client;
}

const PersonalTrainings: React.FC<PersonalTrainingsProps> = ({ client }) => {
    return (
        <div>
            {/* Implementation for Personal Trainings */}
            <p>Personal Trainings for {client.clientName}</p>
        </div>
    );
};

export default PersonalTrainings;