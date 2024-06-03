import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Client, NewClientBodyTest, DictBodyTest } from '../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import ClientBodyTestsService from '../../../services/bodyTests/ClientBodyTestsService';
import DictBodyTestService from '../../../services/dict/DictBodyTestService';

interface ClientDetailsProps {
    client: Client;
}

const AddClientBodyTest: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    const [dictBodyTests, setDictBodyTests] = useState<DictBodyTest[]>([]);

    useEffect(() => {
        DictBodyTestService.getDictBodyTest()
            .then(response => {
                setDictBodyTests(response.data);
            })
            .catch(error => {
                console.error('Błąd podczas pobierania danych:', error);
            });
    }, []);

    // Walidacja za pomocą Yup
    const validationSchema = Yup.object({
        dictBodyTestId: Yup.number().required(t('validation.this_field_is_required')),
        result: Yup.string().required(t('validation.this_field_is_required')),
    });

    // Inicjalne wartości formularza
    const initialValues = {
        id: crypto.randomUUID(), // Używamy crypto.randomUUID() bez toString
        clientId: client.id,
        dictBodyTestId: 0, // Ustawiamy na 0 jako domyślną wartość
        result: ''
    };

    // Obsługa submita formularza
    const handleSubmit = (values: { id: string; clientId: string; dictBodyTestId: number; result: string }) => {
        const requestBody: NewClientBodyTest = {
            id: values.id,
            clientId: values.clientId,
            dictBodyTestId: values.dictBodyTestId,
            result: values.result
        };
        ClientBodyTestsService.postClientBodyTestPerUser(requestBody)
            .then(response => {
                console.log('Odpowiedź z serwera:', response);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
    };

    return (
        <div>
            <h2>{t('navigation.body_tests')}</h2>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                {({ setFieldValue }) => (
                    <Form>
                        <div>
                            <label htmlFor="dictBodyTestId">{t('dictionaries.body_tests_name')}</label>
                            <Field as="select" name="dictBodyTestId" onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setFieldValue('dictBodyTestId', Number(e.target.value))}>
                                <option value="">{t('dictionaries.select_body_test')}</option>
                                {dictBodyTests.map(test => (
                                    <option key={test.id} value={test.id}>
                                        {test.name}
                                    </option>
                                ))}
                            </Field>
                            <ErrorMessage name="dictBodyTestId" component="div" />
                        </div>
                        <div>
                            <label htmlFor="result">{t('dictionaries.result_for_test')}</label>
                            <Field name="result" type="text" />
                            <ErrorMessage name="result" component="div" />
                        </div>
                        <div>
                            <button type="submit">{t('buttons.add')}</button>
                        </div>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddClientBodyTest;
