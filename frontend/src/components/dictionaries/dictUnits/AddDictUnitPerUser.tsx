import React, { useState } from "react";
import { DictUnits, NewDictUnit } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/dict/DictUnitsService';
import * as Yup from 'yup';
import { LANGUAGES, Language } from "../../../translations/Languages";

type Props = {
    dictUnits: DictUnits[];
    onAddUnit: (newUnit: DictUnits) => void;
};

const AddDictUnitPerUser: React.FC<Props> = ({ dictUnits, onAddUnit }) => {
    const [selectedLanguage, setSelectedLanguage] = useState("en");

    const postDictUnit = (values: { newDictUnitName: string, newDictUnitShortcut: string }) => {
        const isNameExisting = dictUnits.some(unit => unit.name === values.newDictUnitName);
        if (!isNameExisting) {
            const newDictUnit: NewDictUnit = {
                id: crypto.randomUUID(),
                name: { [selectedLanguage]: values.newDictUnitName },
                shortcut: { [selectedLanguage]: values.newDictUnitShortcut }
            };
            Service.postDictUnitPerUser(newDictUnit)
                .then(response => {
                    const addedUnit: DictUnits = {
                        id: newDictUnit.id,
                        dict: "PER_USER",
                        dict_id: crypto.randomUUID(),
                        name: values.newDictUnitName,
                        shortcut: values.newDictUnitShortcut
                    };
                    onAddUnit(addedUnit);
                })
                .catch(error => {
                    console.error('Error sending the request:', error);
                });
        } else {
            console.log("Name already exists:", values.newDictUnitName);
        }
    };

    return (
        <div>
            <Formik
                initialValues={{
                    newDictUnitName: '',
                    newDictUnitShortcut: ''
                }}
                validationSchema={Yup.object({
                    newDictUnitName: Yup.string()
                        .required('Pole jest wymagane')
                        .notOneOf(dictUnits.map(item => item.name), 'Nazwa już istnieje'),
                    newDictUnitShortcut: Yup.string()
                        .required('Pole jest wymagane')
                })}
                onSubmit={(values, { setSubmitting }) => {
                    postDictUnit(values);
                    setSubmitting(false);
                }}
            >
                {formik => (
                    <Form>
                        <div>
                            <label htmlFor="language">Language</label>
                            <select
                                id="language"
                                value={selectedLanguage}
                                onChange={(e) => setSelectedLanguage(e.target.value)}
                            >
                                {LANGUAGES.map(lang => (
                                    <option key={lang.code} value={lang.code}>
                                        {lang.label}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <Field name="newDictUnitName" type="text" className="form-control" />
                        <ErrorMessage name="newDictUnitName" component="div" className="error" />

                        <Field name="newDictUnitShortcut" type="text" className="form-control" />
                        <ErrorMessage name="newDictUnitShortcut" component="div" className="error" />

                        <button type="submit" disabled={formik.isSubmitting}>Add</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddDictUnitPerUser;
