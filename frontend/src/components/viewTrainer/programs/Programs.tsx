import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Program } from '../../../types/types';
import AddProgramForm from './addProgram/AddProgramForm';
import ProgramDetails from './ProgramDetails';
import ProgramsService from '../../../services/programs';

const Programs = () => {
  const [programs, setPrograms] = useState<Program[]>([]);
  const [selectedProgram, setSelectedProgram] = useState<Program | null>(null);
  const [showForm, setShowForm] = useState<boolean>(false);
  const { t } = useTranslation('global');

  useEffect(() => {
    fetchPrograms();
  }, []);

  const fetchPrograms = async () => {
    try {
      const response = await ProgramsService.getPrograms();
      if (Array.isArray(response.data)) {
        setPrograms(response.data);
      } else {
        console.error('Unexpected response data format:', response.data);
      }
    } catch (error) {
      console.error('Error fetching programs:', error);
    }
  };

  const handleProgramClick = (program: Program) => {
    setSelectedProgram(program);
    setShowForm(false);
  };

  const handleProgramAdded = () => {
    fetchPrograms();
    setShowForm(false);
  };

  const handleProgramDeleted = () => {
    fetchPrograms();
    setSelectedProgram(null);
  };

  return (
    <div style={{ display: 'flex' }}>
      <div style={{ width: '20%', padding: '10px', borderRight: '1px solid #ccc' }}>
        <button onClick={() => setShowForm(!showForm)}>{t('buttons.add_program')}</button>
        <ul id="programsList">
          {programs.map(program => (
            <li key={program.id} onClick={() => handleProgramClick(program)}>
              <span className={selectedProgram?.id === program.id ? 'selected-program' : ''}>
                {program.programName}
              </span>
            </li>
          ))}
        </ul>
      </div>
      <div style={{ width: '80%', padding: '10px' }}>
        {showForm ? (
          <AddProgramForm onProgramAdded={handleProgramAdded} existingPrograms={programs} />
        ) : selectedProgram ? (
          <ProgramDetails program={selectedProgram} onProgramDeleted={handleProgramDeleted} />
        ) : (
          <div>{t('programs.select_program_message')}</div>
        )}
      </div>
    </div>
  );
};

export default Programs;
