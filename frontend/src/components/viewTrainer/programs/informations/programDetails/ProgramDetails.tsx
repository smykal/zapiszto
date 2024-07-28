import React, { useEffect, useState } from 'react';
import ProgramsService from '../../../../../services/programs';
import ClientsService from '../../../../../services/clients';
import { ProgramDetails as ProgramDetailsType, Client } from '../../../../../types/types';
import CurrentUserGoals from '../currentUserGoals/CurrentUserGoals';
import EditableSelectCell from '../../../../../common/EditableSelectCell';

interface ProgramDetailsProps {
  programId: string;
}

const ProgramDetails: React.FC<ProgramDetailsProps> = ({ programId }) => {
  const [programDetails, setProgramDetails] = useState<ProgramDetailsType | null>(null);
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchProgramDetails = async () => {
      try {
        const response = await ProgramsService.getProgramDetails(programId);
        setProgramDetails(response.data);
      } catch (error) {
        console.error('Error fetching program details:', error);
      } finally {
        setLoading(false);
      }
    };

    const fetchClients = async () => {
      try {
        const response = await ClientsService.getClients();
        setClients(response.data);
      } catch (error) {
        console.error('Error fetching clients:', error);
      }
    };

    fetchProgramDetails();
    fetchClients();
  }, [programId]);

  const handleAssignClient = async (newAssignedClient: string) => {
    if (programDetails) {
      try {
        await ProgramsService.updateProgramAssignedClient(programId, newAssignedClient);
        setProgramDetails({ ...programDetails, assignedClient: newAssignedClient });
      } catch (error) {
        console.error('Error updating assigned client:', error);
      }
    }
  };

  const getClientName = (clientId: string) => {
    const client = clients.find(client => client.id === clientId);
    return client ? client.clientName : 'Unassigned';
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!programDetails) {
    return <p>Program details not found.</p>;
  }

  const clientNames = clients.map(client => client.clientName);

  return (
    <div>
      <p>
        <strong>Assigned Client:</strong> 
        <EditableSelectCell 
          value={getClientName(programDetails.assignedClient)} 
          options={clientNames} 
          onSave={(newValue) => {
            const selectedClient = clients.find(client => client.clientName === newValue);
            if (selectedClient) {
              handleAssignClient(selectedClient.id);
            }
          }} 
        />
      </p>
      {programDetails.assignedClient && <CurrentUserGoals clientId={programDetails.assignedClient} />}
    </div>
  );
};

export default ProgramDetails;
