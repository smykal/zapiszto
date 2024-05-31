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
        name: Yup.string().required(t('validation.required')),
        description: Yup.string().required(t('validation.required')),
        purpose: Yup.string().required(t('validation.required')),
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
            <h2>{t('body_tests.title')}</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                <Form>
                    <div>
                        <label htmlFor="name">{t('body_tests.name')}</label>
                        <Field name="name" type="text" />
                        <ErrorMessage name="name" component="div" />
                    </div>
                    <div>
                        <label htmlFor="description">{t('body_tests.description')}</label>
                        <Field name="description" type="text" />
                        <ErrorMessage name="description" component="div" />
                    </div>
                    <div>
                        <label htmlFor="purpose">{t('body_tests.purpose')}</label>
                        <Field name="purpose" type="text" />
                        <ErrorMessage name="purpose" component="div" />
                    </div>
                    <div>
                        <button type="submit">{t('body_tests.submit')}</button>
                    </div>
                </Form>
            </Formik>
        </div>
    );
};

export default AddNewBodyTest;
