import React, { useEffect, useState } from 'react';
import SessionService from '../../../../../services/sessions/SessionsService'
import { SessionDto } from '../../../../../types/types';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import { useTranslation } from 'react-i18next';
import GetExerciseSession from '../exercises/GetExerciseSession';

interface SessionProps {
  microcycleId: string;
}

const Session: React.FC<SessionProps> = ({ microcycleId }) => {
  const { t } = useTranslation('global');
  const [sessions, setSessions] = useState<SessionDto[]>([]);
  const [message, setMessage] = useState<string>('');

  useEffect(() => {
    loadSessions();
  }, [microcycleId]);

  const loadSessions = () => {
    SessionService.getSessions(microcycleId)
      .then(response => {
        setSessions(response.data);
      })
      .catch(error => {
        console.error('Error loading sessions:', error);
        setMessage(t('session.loading_error'));
      });
  };

  return (
    <div>
      <strong>{t('session.select_session')}</strong>
      {message && <p>{message}</p>}
      <Tabs>
        <TabList>
          {sessions.map((session, index) => (
            <Tab key={session.id}>{t('session.order')}: {session.orderId}</Tab>
          ))}
        </TabList>
        {sessions.map((session, index) => (
          <TabPanel key={session.id}>
            <h3>{t('session.details_for')} {session.orderId}</h3>
            <p><strong>{t('table.label')}:</strong> {session.label}</p>
            <GetExerciseSession session_id={session.id} />
          </TabPanel>
        ))}
      </Tabs>
    </div>
  );
};

export default Session;
