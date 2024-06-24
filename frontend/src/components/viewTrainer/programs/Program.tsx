import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import ProgramInformations from './informations/programInformations/ProgramInformations';
import DeleteProgram from './informations/programInformations/DeleteProgram';
import Collapsible from 'react-collapsible';
import ProgramDetails from './informations/programDetails/ProgramDetails';

interface ProgramDetailsProps {
  program: Program;
  onProgramDeleted: () => void;
  onSaveProgramName: (id: string, newName: string) => void;
}

const ProgramInfo: React.FC<ProgramDetailsProps> = ({ program, onProgramDeleted, onSaveProgramName }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("programs.informations")} open={true}>
        <ProgramInformations program={program} onSaveProgramName={onSaveProgramName} />
        <ProgramDetails programId={program.id} />
        <DeleteProgram program={program} onProgramDeleted={onProgramDeleted} />
      </Collapsible>
    </div>
  );
};

export default ProgramInfo;
