import React, { useEffect, useState } from 'react';
import SessionService from '../../../../../services/sessions/SessionsService';
import { SessionDto } from '../../../../../types/types';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import { useTranslation } from 'react-i18next';
import GetExerciseSession from '../exercises/GetExerciseSession';
import Modal from '../../../../../constants/Modal';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

interface SessionProps {
  microcycleId: string;
}

const Session: React.FC<SessionProps> = ({ microcycleId }) => {
  const { t } = useTranslation('global');
  const [sessions, setSessions] = useState<SessionDto[]>([]);
  const [message, setMessage] = useState<string>('');
  const [showModal, setShowModal] = useState<boolean>(false);
  const [currentSession, setCurrentSession] = useState<SessionDto | null>(null);
  const [newDateTime, setNewDateTime] = useState<Date | null>(null);

  useEffect(() => {
    loadSessions();
  }, [microcycleId]);

  const loadSessions = () => {
    SessionService.getSessions(microcycleId)
      .then(response => {
        const sortedSessions = response.data.sort((a: SessionDto, b: SessionDto) => a.orderId - b.orderId);
        setSessions(sortedSessions);
      })
      .catch(error => {
        console.error('Error loading sessions:', error);
        setMessage(t('session.loading_error'));
      });
  };

  const handleOpenModal = (session: SessionDto) => {
    setCurrentSession(session);
    setNewDateTime(session.dateTime ? new Date(session.dateTime) : null);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setCurrentSession(null);
    setNewDateTime(null);
  };

  const handleSaveDateTime = () => {
    if (currentSession && newDateTime) {
      SessionService.updateSessionDateTime(currentSession.id, newDateTime)
        .then(() => {
          setSessions(prevSessions =>
            prevSessions.map(session =>
              session.id === currentSession.id ? { ...session, dateTime: newDateTime.toISOString() } : session
            ).sort((a, b) => a.orderId - b.orderId) // Sortowanie po aktualizacji
          );
          handleCloseModal();
        })
        .catch(error => {
          console.error('Error updating dateTime:', error);
          setMessage(t('session.update_error'));
        });
    }
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <Tabs>
        <TabList>
          {sessions.map((session) => (
            <Tab key={session.id}>{t('session.session')}: {session.orderId}</Tab>
          ))}
        </TabList>
        {sessions.map((session) => (
          <TabPanel key={session.id}>
            <p><strong>{t('table.label')}:</strong> {session.label}</p>
            <p>
              <strong>{t('session.session_date')}:</strong> {session.dateTime ? new Date(session.dateTime).toLocaleString() : t('session.no_date')}
              <button onClick={() => handleOpenModal(session)}>{t('buttons.edit')}</button>
            </p>
            <GetExerciseSession session_id={session.id} />
          </TabPanel>
        ))}
      </Tabs>
      <Modal show={showModal} onClose={handleCloseModal} title={t('session.edit_date')}>
        <DatePicker
          selected={newDateTime}
          onChange={(date: Date) => setNewDateTime(date)}
          showTimeSelect
          dateFormat="Pp"
        />
        <button onClick={handleSaveDateTime}>{t('buttons.save')}</button>
      </Modal>
    </div>
  );
};

export default Session;
