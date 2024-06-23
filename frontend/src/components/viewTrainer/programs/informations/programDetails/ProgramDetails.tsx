import React, { useEffect, useState } from 'react';
import ProgramsService from '../../../../../services/programs';
import ClientsService from '../../../../../services/clients';
import { ProgramDetails as ProgramDetailsType, Client } from '../../../../../types/types';

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

  const handleAssignClient = async (e: React.ChangeEvent<HTMLSelectElement>) => {
    const assignedClient = e.target.value;
    if (programDetails) {
      try {
        await ProgramsService.updateProgramAssignedClient(programId, assignedClient);
        setProgramDetails({ ...programDetails, assignedClient });
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

  return (
    <div>
      <h2>Program Details</h2>
      <p><strong>ID:</strong> {programId}</p>
      <p><strong>Assigned Client:</strong> {getClientName(programDetails.assignedClient)}</p>
      <p>
        <strong>Change Assigned Client:</strong>
        <select onChange={handleAssignClient} value={programDetails.assignedClient || ''}>
          <option value="">Select a client</option>
          {clients.map(client => (
            <option key={client.id} value={client.id}>
              {client.clientName}
            </option>
          ))}
        </select>
      </p>
    </div>
  );
};

export default ProgramDetails;
