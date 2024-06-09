import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import UpdateClientUser from './UpdateClientUser';
import ClientsService from '../../../../services/bodyParams/'; // Poprawiony import serwisu

interface ClientDetailsProps {
  client: Client;
}

const ClientInformations: React.FC<ClientDetailsProps> = ({ client }) => {
  const { t } = useTranslation('global');
  const [userId, setUserId] = useState(client.userId);
  const [age, setAge] = useState<string | null>(null);
  const [gender, setGender] = useState<string | null>(null);

  useEffect(() => {
    setUserId(client.userId);

    if (client.userId !== 0) {
      ClientsService.getAgeByUserId(client.userId)
        .then(response => {
          setAge(response.data.age);
        })
        .catch(error => {
          console.error('Error fetching age:', error);
        });

      ClientsService.getGenderByUserId(client.userId)
        .then(response => {
          setGender(response.data.gender);
        })
        .catch(error => {
          console.error('Error fetching gender:', error);
        });
    }
  }, [client.userId]);

  return (
    <div>
      <p>ID: {client.id}</p>
      <p>{t('clients.client_name')}: {client.clientName}</p>
      {age && <p>{t('clients.age')}: {age}</p>}
      {gender && <p>{t('clients.gender')}: {gender}</p>}
      <p>birthdate: </p>
      <p>
        {t('clients.client_assigned_user')}: {userId === 0 ? t('clients.user_is_not_assigned') : userId}
      </p>
      {userId === 0 && <UpdateClientUser client={client} />}
    </div>
  );
};

export default ClientInformations;
