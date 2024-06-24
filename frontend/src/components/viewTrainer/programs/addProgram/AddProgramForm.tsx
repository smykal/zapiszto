import React from 'react';
import { useTranslation } from 'react-i18next';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import ProgramsService from '../../../../services/programs/';
import { NewProgram, Program } from '../../../../types/types';

interface AddProgramFormProps {
  onProgramAdded: () => void;
  existingPrograms: Program[];
}

const AddProgramForm: React.FC<AddProgramFormProps> = ({ onProgramAdded, existingPrograms }) => {
  const { t } = useTranslation('global');

  const handleAddProgram = async (values: NewProgram, { setSubmitting, resetForm }: any) => {
    try {
      const newProgramWithId = { ...values, id: crypto.randomUUID(), createdBy: 0 }; // Set appropriate createdBy
      await ProgramsService.postNewProgram(newProgramWithId);
      resetForm();
      onProgramAdded();
    } catch (error) {
      console.error('Error adding program:', error);
    } finally {
      setSubmitting(false);
    }
  };

  const validationSchema = Yup.object().shape({
    programName: Yup.string()
      .max(30, t('validation.program_name_too_long')) // Add max length validation
      .test('unique-programName', t('programs.program_name_exists'), value => {
        return !existingPrograms.some(program => program.programName === value);
      })
      .required(t('validation.this_field_is_required')),
  });

  return (
    <div>
      <h2>{t('buttons.add_program')}</h2>
      <Formik
        initialValues={{ id: '', programName: '', createdBy: 0 }}
        validationSchema={validationSchema}
        onSubmit={handleAddProgram}
      >
        {({ isSubmitting }) => (
          <Form>
            <div>
              <label>{t('programs.program_name')}:</label>
              <Field type="text" name="programName" />
              <ErrorMessage name="programName" component="div"/>
            </div>
            <button type="submit" disabled={isSubmitting}>
              {t('buttons.add')}
            </button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default AddProgramForm;
