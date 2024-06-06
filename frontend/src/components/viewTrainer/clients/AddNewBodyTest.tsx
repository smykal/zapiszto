import React from 'react';
import { useTranslation } from 'react-i18next';
import { Client, NewDictBodyTest } from '../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import ClientsService from '../../../services/clients';

interface ClientDetailsProps {
    client: Client
}

const AddNewBodyTest: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');

    // Walidacja za pomocą Yup
    const validationSchema = Yup.object({
        name: Yup.string().required(t('validation.this_field_is_required')),
        description: Yup.string().required(t('validation.this_field_is_required')),
        purpose: Yup.string().required(t('validation.this_field_is_required')),
    });

    // Inicjalne wartości formularza
    const initialValues = {
        name: '',
        description: '',
        purpose: '',
    };

    // Obsługa submita formularza
    const handleSubmit = (values: { name: string, description: string, purpose: string }) => {
        const requestBody: NewDictBodyTest = {
            name: values.name,
            description: values.description,
            purpose: values.purpose,
        };
        ClientsService.postNewDictBodyTest(requestBody)
            .then(response => {
                console.log('Odpowiedź z serwera:', response);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
    };

    return (
        <div>
            <h2>{t('clients.add_body_test')}</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                <Form>
                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="name">{t('dictionaries.body_tests_name')}</label>
                            <Field name="name" type="text" />
                            <ErrorMessage name="name" component="div" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">{t('dictionaries.body_tests_description')}</label>
                            <Field name="description" type="text" />
                            <ErrorMessage name="description" component="div" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="purpose">{t('dictionaries.body_tests_purpose')}</label>
                            <Field name="purpose" type="text" />
                            <ErrorMessage name="purpose" component="div" />
                        </div>
                    </div>
                    <div>
                        <button type="submit">{t('buttons.add')}</button>
                    </div>
                </Form>
            </Formik>
        </div>
    );
};

export default AddNewBodyTest;
