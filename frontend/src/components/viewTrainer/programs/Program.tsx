import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import ProgramInformations from './informations/programInformations/ProgramInformations';
import DeleteProgram from './informations/programInformations/DeleteProgram';
import Collapsible from 'react-collapsible';
import ProgramDetails from './informations/programDetails/ProgramDetails';
import Makrocykl from './informations/makrocykl/Makrocykl';
import DuplicateProgram from './informations/programInformations/DuplicateProgram';

interface ProgramDetailsProps {
  program: Program;
  onProgramDeleted: () => void;
  onSaveProgramName: (id: string, newName: string) => void;
  onDuplicateProgram: (id: string, newName: string) => void;
}

const ProgramInfo: React.FC<ProgramDetailsProps> = ({ program, onProgramDeleted, onSaveProgramName, onDuplicateProgram }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("programs.informations")} open={true}>
        <ProgramInformations program={program} onSaveProgramName={onSaveProgramName} />
        <ProgramDetails programId={program.id} />

        <DeleteProgram program={program} onProgramDeleted={onProgramDeleted} />
        <DuplicateProgram programId={program.id} onDuplicateProgram={onDuplicateProgram} />
      </Collapsible>

      <Collapsible trigger={t("programs.macrocicle")} open={false}>
        <Makrocykl programId={program.id} />
      </Collapsible>
    </div>
  );
};

export default ProgramInfo;
