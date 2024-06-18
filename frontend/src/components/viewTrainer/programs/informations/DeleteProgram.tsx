import React from 'react';
import ProgramsService from '../../../../services/programs/';
import { Program } from '../../../../types/types';
import { useTranslation } from 'react-i18next';

interface DeleteProgramProps {
  program: Program;
  onProgramDeleted: () => void;
}

const DeleteProgram: React.FC<DeleteProgramProps> = ({ program, onProgramDeleted }) => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    const confirmMessage = `${t('programs.confirm_delete')} ${program.programName}`;

    if (window.confirm(confirmMessage)) {
      try {
        await ProgramsService.deleteProgram(program.id);
        onProgramDeleted();
        console.log('Program deleted successfully');
      } catch (error) {
        console.error('Error deleting program:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('buttons.delete_program')}
    </button>
  );
};

export default DeleteProgram;
