import React from 'react';
import SessionService from '../../../../../services/sessions/SessionsService';
import { SessionDto } from '../../../../../types/types';
import { useTranslation } from 'react-i18next';

interface DeleteSessionProps {
  session: SessionDto;
  onSessionDeleted: () => void;
}

const DeleteSession: React.FC<DeleteSessionProps> = ({ session, onSessionDeleted }) => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    const confirmMessage = `${t('session.confirm_delete')} ${session.label}`;

    if (window.confirm(confirmMessage)) {
      try {
        await SessionService.deleteSession(session.id);
        onSessionDeleted();
        console.log('Session deleted successfully');
      } catch (error) {
        console.error('Error deleting session:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('buttons.delete')}
    </button>
  );
};

export default DeleteSession;
