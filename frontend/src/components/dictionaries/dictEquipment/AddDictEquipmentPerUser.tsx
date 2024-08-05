import React, { useState } from "react";
import { DictEquipment, NewDictEquipment } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import DictEquipmentService from '../../../services/dict/DictEquipmentService';
import * as Yup from 'yup';
import { LANGUAGES, Language } from "../../../translations/Languages";

type Props = {
    dictEquipment: DictEquipment[];
    onAddEquipment: (newEquipment: DictEquipment) => void;
};

const AddDictEquipmentPerUser: React.FC<Props> = ({ dictEquipment, onAddEquipment }) => {
    const [selectedLanguage, setSelectedLanguage] = useState("en");

    const postDictEquipment = (values: { newDictEquipmentName: string }) => {
        const newDictEquipment: NewDictEquipment = {
            id: crypto.randomUUID(),
            name: { [selectedLanguage]: values.newDictEquipmentName }
        };
        DictEquipmentService.postDictEquipmentPerUser(newDictEquipment)
            .then(response => {
                const addedEquipment: DictEquipment = {
                    id: crypto.randomUUID(),
                    dict: "PER_USER",
                    dict_id: crypto.randomUUID(),
                    name: values.newDictEquipmentName
                };
                onAddEquipment(addedEquipment);
            })
            .catch(error => {
                console.error('Error sending the request:', error);
            });
    };

    return (
        <div>
            <Formik
                initialValues={{
                    newDictEquipmentName: '',
                }}
                validationSchema={Yup.object({
                    newDictEquipmentName: Yup.string()
                        .required('Pole jest wymagane')
                })}
                onSubmit={(values, { setSubmitting }) => {
                    postDictEquipment(values);
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
                        <Field name="newDictEquipmentName" type="text" className="form-control" />
                        <ErrorMessage name="newDictEquipmentName" component="div" className="error" />
                        <button type="submit" disabled={formik.isSubmitting}>Add</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddDictEquipmentPerUser;
