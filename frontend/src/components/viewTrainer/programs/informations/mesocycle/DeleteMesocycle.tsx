import React from 'react';
import { useTranslation } from 'react-i18next';
import MesocycleService from '../../../../../services/mesocycle/MesocycleService';
import { MesocycleDto } from '../../../../../types/types';

interface DeleteMesocycleProps {
  mesocycle: MesocycleDto;
  onMesocycleDeleted: () => void;
}

const DeleteMesocycle: React.FC<DeleteMesocycleProps> = ({ mesocycle, onMesocycleDeleted }) => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    const confirmMessage = `${t('mesocycle.confirm_delete')} ${mesocycle.label}`;

    if (window.confirm(confirmMessage)) {
      try {
        await MesocycleService.deleteMesocycle(mesocycle.id);
        onMesocycleDeleted();
        console.log('Mesocycle deleted successfully');
      } catch (error) {
        console.error('Error deleting mesocycle:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('buttons.delete')}
    </button>
  );
};

export default DeleteMesocycle;
