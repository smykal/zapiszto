import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Client } from '../../../../types/types';
import GoalsService from '../../../../services/goals/GoalsService';
import BodyParamsService from '../../../../services/bodyParams/';
import DictBodyTestService from '../../../../services/dict/DictBodyTestService';
import DictUnitsService from '../../../../services/dict/DictUnitsService';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { DictBodyParam, DictBodyTest, DictUnits } from '../../../../types/types';
import * as Yup from 'yup';

interface ClientDetailsProps {
    client: Client;
}

const AddGoals: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    const [bodyParameters, setBodyParameters] = useState<DictBodyParam[]>([]);
    const [bodyTests, setBodyTests] = useState<DictBodyTest[]>([]);
    const [units, setUnits] = useState<DictUnits[]>([]);
    const [selectedOption, setSelectedOption] = useState<'bodyParam' | 'bodyTest' | null>(null);

    useEffect(() => {
        BodyParamsService.getDictBodyParams()
            .then((response: { data: DictBodyParam[] }) => {
                setBodyParameters(response.data);
            })
            .catch((error: any) => {
                console.error('Error fetching body parameters:', error);
            });
    }, []);

    useEffect(() => {
        DictBodyTestService.getDictBodyTest()
            .then((response: { data: DictBodyTest[] }) => {
                setBodyTests(response.data);
            })
            .catch((error: any) => {
                console.error('Error fetching body tests:', error);
            });
    }, []);

    useEffect(() => {
        DictUnitsService.getDictUnits()
            .then((response: { data: DictUnits[] }) => {
                setUnits(response.data);
            })
            .catch((error: any) => {
                console.error('Error fetching units:', error);
            });
    }, []);

    // Walidacja formularza za pomocą Yup
    const validationSchema = Yup.object({
        dictBodyParamId: Yup.number().nullable(),
        dictBodyTestId: Yup.number().nullable(),
        dictUnitId: Yup.number().required(t('required')),
        action: Yup.string().required(t('required')),
        value: Yup.string().required(t('required')),
        goalDate: Yup.string().required(t('required')),
    }).test('bodyParamOrTest', t('required'), function (value) {
        const { dictBodyParamId, dictBodyTestId } = value;
        if (selectedOption === 'bodyParam' && !dictBodyParamId) {
            return this.createError({ path: 'dictBodyParamId', message: t('required') });
        }
        if (selectedOption === 'bodyTest' && !dictBodyTestId) {
            return this.createError({ path: 'dictBodyTestId', message: t('required') });
        }
        return true;
    });

    return (
        <div>
            <h2>{t('clients.goals')}</h2>
            <Formik
                initialValues={{
                    id: crypto.randomUUID(),
                    dictBodyParamId: null,
                    dictBodyTestId: null,
                    dictUnitId: '',
                    action: '',
                    value: '',
                    goalDate: '',
                }}
                validationSchema={validationSchema}
                onSubmit={(values, { setSubmitting, resetForm }) => {
                    const newGoal = {
                        ...values,
                        clientId: client.id,
                        dictBodyParamId: values.dictBodyParamId ? Number(values.dictBodyParamId) : null,
                        dictBodyTestId: values.dictBodyTestId ? Number(values.dictBodyTestId) : null,
                        dictUnitId: String(values.dictUnitId),
                    };
                    GoalsService.postNewGoal(newGoal)
                        .then(() => {
                            console.log('Goal added successfully');
                            resetForm();
                        })
                        .catch(error => {
                            console.error('Error adding goal:', error);
                        })
                        .finally(() => {
                            setSubmitting(false);
                        });
                }}
            >
                {({ isSubmitting, setFieldValue }) => (
                    <Form>
                        <div>
                            <label>
                                <Field
                                    type="radio"
                                    name="selection"
                                    value="bodyParam"
                                    checked={selectedOption === 'bodyParam'}
                                    onChange={() => setSelectedOption('bodyParam')}
                                />
                                {t('clients.select_body_param')}
                            </label>
                            <label>
                                <Field
                                    type="radio"
                                    name="selection"
                                    value="bodyTest"
                                    checked={selectedOption === 'bodyTest'}
                                    onChange={() => setSelectedOption('bodyTest')}
                                />
                                {t('clients.select_body_test')}
                            </label>
                        </div>

                        <div className="form-row">
                            {selectedOption === 'bodyParam' && (
                                <div className="form-group">
                                    <label htmlFor="dictBodyParamId">{t('clients.select_body_param')}</label>
                                    <Field as="select" name="dictBodyParamId" onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setFieldValue('dictBodyParamId', Number(e.target.value))}>
                                        <option value="" disabled hidden>{t('clients.select_body_param')}</option>
                                        {bodyParameters.map(param => (
                                            <option key={param.id} value={param.id}>
                                                {param.name}
                                            </option>
                                        ))}
                                    </Field>
                                    <ErrorMessage name="dictBodyParamId" component="div" />
                                </div>
                            )}

                            {selectedOption === 'bodyTest' && (
                                <div className="form-group">
                                    <label htmlFor="dictBodyTestId">{t('clients.select_body_test')}</label>
                                    <Field as="select" name="dictBodyTestId" onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setFieldValue('dictBodyTestId', Number(e.target.value))}>
                                        <option value="" disabled hidden>{t('clients.select_body_test')}</option>
                                        {bodyTests.map(test => (
                                            <option key={test.id} value={test.id}>
                                                {test.name}
                                            </option>
                                        ))}
                                    </Field>
                                    <ErrorMessage name="dictBodyTestId" component="div" />
                                </div>
                            )}

                            <div className="form-group">
                                <label htmlFor="dictUnitId">{t('clients.select_unit')}</label>
                                <Field as="select" name="dictUnitId" onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setFieldValue('dictUnitId', Number(e.target.value))}>
                                    <option value="" disabled hidden>{t('clients.select_unit')}</option>
                                    {units.map(unit => (
                                        <option key={unit.id} value={unit.id}>
                                            {unit.name} ({unit.shortcut})
                                        </option>
                                    ))}
                                </Field>
                                <ErrorMessage name="dictUnitId" component="div" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="action">{t('clients.action')}</label>
                                <Field type="text" name="action" />
                                <ErrorMessage name="action" component="div" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="value">{t('clients.value')}</label>
                                <Field type="text" name="value" />
                                <ErrorMessage name="value" component="div" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="goalDate">{t('clients.date')}</label>
                                <Field type="date" name="goalDate" />
                                <ErrorMessage name="goalDate" component="div" />
                            </div>

                            <button type="submit" disabled={isSubmitting}>
                                {t('buttons.add')}
                            </button>
                        </div>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddGoals;
