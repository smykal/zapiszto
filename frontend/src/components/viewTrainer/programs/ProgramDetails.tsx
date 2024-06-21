import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import ProgramInformations from './informations/ProgramInformations';
import DeleteProgram from './informations/DeleteProgram';
import Collapsible from 'react-collapsible';

interface ProgramDetailsProps {
  program: Program;
  onProgramDeleted: () => void;
  onSaveProgramName: (id: string, newName: string) => void;
}

const ProgramDetails: React.FC<ProgramDetailsProps> = ({ program, onProgramDeleted, onSaveProgramName }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("programs.informations")} open={true}>
        <ProgramInformations program={program} onSaveProgramName={onSaveProgramName} />
        <DeleteProgram program={program} onProgramDeleted={onProgramDeleted} />
      </Collapsible>
    </div>
  );
};

export default ProgramDetails;
