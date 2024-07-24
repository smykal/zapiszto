import React, { useEffect, useState } from 'react';
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import PersonalTrainingsService from '../../../../../services/personalTrainings/PersonalTrainingsService';
import { Client, ClientProgramDto, ClientProgramMacrocycleDto, ClientProgramMesocycleDto, ClientProgramMicrocycleDto, ClientProgramSessionDto, ClientProgramExerciseDto } from '../../../../../types/types';


interface PersonalTrainingsProps {
    client: Client;
}

const PersonalTrainings: React.FC<PersonalTrainingsProps> = ({ client }) => {
    const [clientPrograms, setClientPrograms] = useState<ClientProgramDto[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchClientPrograms = async () => {
            try {
                const response = await PersonalTrainingsService.getClientProgram(client.id);
                setClientPrograms(response.data);
            } catch (error) {
                console.error('Error fetching client programs:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchClientPrograms();
    }, [client.id]);

    if (loading) {
        return <p>Loading...</p>;
    }

    if (clientPrograms.length === 0) {
        return <p>No programs found for client.</p>;
    }

    const renderExercisesTable = (exercises: ClientProgramExerciseDto[]) => (
        <table>
            <thead>
                <tr>
                    <th>Order</th>
                    <th>Purpose</th>
                    <th>Category</th>
                    <th>Exercise</th>
                    <th>Weight</th>
                    <th>Weight Unit</th>
                    <th>Repetitions</th>
                    <th>Repetitions Unit</th>
                </tr>
            </thead>
            <tbody>
                {exercises.map(exercise => (
                    <tr key={exercise.orderId}>
                        <td>{exercise.orderId}</td>
                        <td>{exercise.purpose}</td>
                        <td>{exercise.category}</td>
                        <td>{exercise.exercise}</td>
                        <td>{exercise.weight}</td>
                        <td>{exercise.weightUnit}</td>
                        <td>{exercise.repetitions}</td>
                        <td>{exercise.repetitionsUnit}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );

    const renderTabs = () => (
        <Tabs>
            <TabList>
                {clientPrograms.map((program, index) => (
                    <Tab key={index}>{program.name}</Tab>
                ))}
            </TabList>

            {clientPrograms.map((program, programIndex) => (
                <TabPanel key={programIndex}>
                    <Tabs>
                        <TabList>
                            {program.macrocycles.map((macrocycle, macrocycleIndex) => (
                                <Tab key={macrocycleIndex}>Macrocycle {macrocycleIndex + 1}</Tab>
                            ))}
                        </TabList>

                        {program.macrocycles.map((macrocycle, macrocycleIndex) => (
                            <TabPanel key={macrocycleIndex}>
                                <Tabs>
                                    <TabList>
                                        {macrocycle.mesocycles.map((mesocycle, mesocycleIndex) => (
                                            <Tab key={mesocycleIndex}>Mesocycle {mesocycleIndex + 1}</Tab>
                                        ))}
                                    </TabList>

                                    {macrocycle.mesocycles.map((mesocycle, mesocycleIndex) => (
                                        <TabPanel key={mesocycleIndex}>
                                            <Tabs>
                                                <TabList>
                                                    {mesocycle.microcycles.map((microcycle, microcycleIndex) => (
                                                        <Tab key={microcycleIndex}>Microcycle {microcycleIndex + 1}</Tab>
                                                    ))}
                                                </TabList>

                                                {mesocycle.microcycles.map((microcycle, microcycleIndex) => (
                                                    <TabPanel key={microcycleIndex}>
                                                        <Tabs>
                                                            <TabList>
                                                                {microcycle.sessions.map((session, sessionIndex) => (
                                                                    <Tab key={sessionIndex}>Session {sessionIndex + 1}</Tab>
                                                                ))}
                                                            </TabList>

                                                            {microcycle.sessions.map((session, sessionIndex) => (
                                                                <TabPanel key={sessionIndex}>
                                                                    {renderExercisesTable(session.exercises)}
                                                                </TabPanel>
                                                            ))}
                                                        </Tabs>
                                                    </TabPanel>
                                                ))}
                                            </Tabs>
                                        </TabPanel>
                                    ))}
                                </Tabs>
                            </TabPanel>
                        ))}
                    </Tabs>
                </TabPanel>
            ))}
        </Tabs>
    );

    return (
        <div>
            <h2>Personal Trainings for {client.clientName}</h2>
            {renderTabs()}
        </div>
    );
};

export default PersonalTrainings;