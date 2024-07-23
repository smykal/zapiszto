import React from 'react';
import MicrocycleService from '../../../../../services/microcycle/MicrocycleService';
import { MicrocycleDto } from '../../../../../types/types';
import { useTranslation } from 'react-i18next';

interface DeleteMicrocycleProps {
  microcycle: MicrocycleDto;
  onMicrocycleDeleted: () => void;
}

const DeleteMicrocycle: React.FC<DeleteMicrocycleProps> = ({ microcycle, onMicrocycleDeleted }) => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    const confirmMessage = `${t('microcycle.confirm_delete')} ${microcycle.orderId}`;

    if (window.confirm(confirmMessage)) {
      try {
        await MicrocycleService.deleteMicrocycle(microcycle.id);
        onMicrocycleDeleted();
        console.log('Microcycle deleted successfully');
      } catch (error) {
        console.error('Error deleting microcycle:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('buttons.delete_microcycle')}
    </button>
  );
};

export default DeleteMicrocycle;
