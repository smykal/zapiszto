import React, { useState } from "react";
import { DictQuantityType, NewDictQuantityType } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/dict/DictQuantityTypeService';
import * as Yup from 'yup';
import { LANGUAGES, Language } from "../../../translations/Languages"; 

type Props = {
    dictQuantityType: DictQuantityType[];
    onAddQuantityType: (newQuantityType: DictQuantityType) => void;
};

const AddDictQuantityTypePerUser: React.FC<Props> = ({ dictQuantityType, onAddQuantityType }) => {
    const [selectedLanguage, setSelectedLanguage] = useState(LANGUAGES[0].code);

    const postDictQuantityType = (values: { newDictQuantityTypeName: string, newDictQuantityTypeShortcut: string }) => {
        const isNameExisting = dictQuantityType.some(quantityType => quantityType.name === values.newDictQuantityTypeName);
        if (!isNameExisting) {
            const newDictQuantityType: NewDictQuantityType = {
                id: crypto.randomUUID(), // Generate a new UUID
                name: { [selectedLanguage]: values.newDictQuantityTypeName },
                shortcut: { [selectedLanguage]: values.newDictQuantityTypeShortcut }
            };
            Service.postDictQuantityTypePerUser(newDictQuantityType)
            .then(() => {
                console.log("Wysłano nowy typ ilości:", values.newDictQuantityTypeName);
                // Simulate the server response as the service method does not return data
                const newQuantityType: DictQuantityType = {
                    id: newDictQuantityType.id,
                    name: values.newDictQuantityTypeName, // Use the specific name value for the selected language
                    shortcut: values.newDictQuantityTypeShortcut,
                    dict: "PER_USER",
                    dict_id: crypto.randomUUID(), // Simulate the new dict_id
                };
                onAddQuantityType(newQuantityType);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
        } else {
            console.log("Nazwa już istnieje:", values.newDictQuantityTypeName);
        }
    };

    return (
        <div>
            <Formik
                initialValues={{
                    newDictQuantityTypeName: '',
                    newDictQuantityTypeShortcut: ''
                }}
                validationSchema={Yup.object({
                    newDictQuantityTypeName: Yup.string()
                        .required('Pole jest wymagane')
                        .notOneOf(dictQuantityType.map(item => item.name), 'Nazwa już istnieje'),
                    newDictQuantityTypeShortcut: Yup.string()
                        .required('Pole jest wymagane')
                })}
                onSubmit={(values, { setSubmitting }) => {
                    setTimeout(() => {
                        postDictQuantityType(values);
                        setSubmitting(false);
                    }, 400);
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
                                {LANGUAGES.map((lang: Language) => (
                                    <option key={lang.code} value={lang.code}>
                                        {lang.label}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div>
                            <label htmlFor="newDictQuantityTypeName">Name</label>
                            <Field name="newDictQuantityTypeName" type="text" className="form-control" />
                            <ErrorMessage name="newDictQuantityTypeName" component="div" className="error" />
                        </div>
                        <div>
                            <label htmlFor="newDictQuantityTypeShortcut">Shortcut</label>
                            <Field name="newDictQuantityTypeShortcut" type="text" className="form-control" />
                            <ErrorMessage name="newDictQuantityTypeShortcut" component="div" className="error" />
                        </div>
                        <button type="submit" disabled={formik.isSubmitting}>Add</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddDictQuantityTypePerUser;
