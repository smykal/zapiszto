import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../../types/types';

interface ProgramInformationsProps {
  program: Program;
}

const ProgramInformations: React.FC<ProgramInformationsProps> = ({ program }) => {
  const { t } = useTranslation('global');

  return (
    <div>
      <p>ID: {program.id}</p>
      <p>{t('programs.program_name')}: {program.programName}</p>
      <p>{t('programs.created_date')}: {program.createdDate}</p>
      <p>{t('programs.created_by')}: {program.createdBy}</p>
    </div>
  );
};

export default ProgramInformations;
