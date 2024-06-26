import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import ProgramInformations from './informations/programInformations/ProgramInformations';
import DeleteProgram from './informations/programInformations/DeleteProgram';
import Collapsible from 'react-collapsible';
import ProgramDetails from './informations/programDetails/ProgramDetails';
import Makrocykl from './informations/makrocykl/Makrocykl';

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
        <DeleteProgram program={program} onProgramDeleted={onProgramDeleted} />
      </Collapsible>
      
      <Collapsible trigger={t("programs.details")} open={false}>
        <ProgramDetails programId={program.id} />
      </Collapsible>

      <Collapsible trigger={t("programs.macrocicle")} open={false}>
      <Makrocykl programId={program.id} />
      </Collapsible>
    </div>
  );
};

export default ProgramInfo;
