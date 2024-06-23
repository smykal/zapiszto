import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../../../types/types';
import EditableCell from '../../../../../common/EditableCell';

interface ProgramInformationsProps {
  program: Program;
  onSaveProgramName: (id: string, newName: string) => void; // Add a prop for saving the program name
}

const ProgramInformations: React.FC<ProgramInformationsProps> = ({ program, onSaveProgramName }) => {
  const { t } = useTranslation('global');
  const [programName, setProgramName] = useState(program.programName);

  useEffect(() => {
    setProgramName(program.programName);
  }, [program.programName]);

  const handleSaveProgramName = (newName: string) => {
    setProgramName(newName);
    onSaveProgramName(program.id, newName); // Call the save function passed from the parent
  };

  return (
    <div>
      <p>ID: {program.id}</p>
      <p>
        {t('programs.program_name')}:
        <EditableCell value={programName} onSave={handleSaveProgramName} />
      </p>
      <p>{t('programs.created_date')}: {program.createDate}</p>
      <p>{t('programs.created_by')}: {program.createdBy}</p>
    </div>
  );
};

export default ProgramInformations;
