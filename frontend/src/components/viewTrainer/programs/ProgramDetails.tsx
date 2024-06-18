import React from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import ProgramInformations from './informations/ProgramInformations';
import DeleteProgram from './informations/DeleteProgram';
import Collapsible from 'react-collapsible';

interface ProgramDetailsProps {
  program: Program;
  onProgramDeleted: () => void;
}

const ProgramDetails: React.FC<ProgramDetailsProps> = ({ program, onProgramDeleted }) => {
  const { t } = useTranslation('global');
  return (
    <div className="container">
      <Collapsible trigger={t("programs.informations")} open={true}>
        <ProgramInformations program={program} />
        <DeleteProgram program={program} onProgramDeleted={onProgramDeleted} />
      </Collapsible>
    </div>
  );
};

export default ProgramDetails;
