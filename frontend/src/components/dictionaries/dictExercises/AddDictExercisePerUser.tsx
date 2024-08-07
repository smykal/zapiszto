import React, { useState } from "react";
import { DictCategories, DictExercises, NewDictExercises } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";
import { LANGUAGES } from "../../../translations/Languages";

type Props = {
    dictExercises: DictExercises[];
    dictCategories: DictCategories[];
    onAddExercise: (newExercise: DictExercises) => void;
    t: any;
};

const AddDictExercisePerUser: React.FC<Props> = ({ dictExercises, dictCategories, onAddExercise, t }) => {
    const [selectedLanguage, setSelectedLanguage] = useState("en");

    const postDictExercises = (values: { name: string, categoryId: number }) => {
        const isNameExisting = dictExercises.some(exercise => exercise.name === values.name);
        if (!isNameExisting) {
            const newDictExerciseData: NewDictExercises = { 
                id: crypto.randomUUID(),
                name: { [selectedLanguage]: values.name },
                categoryId: values.categoryId || 1
            };
            Service.postDictExercisePerUser(newDictExerciseData)
            .then(() => {
                console.log("Wysłano nowe ćwiczenie:", values.name);
                const newExercise: DictExercises = {
                    id: crypto.randomUUID(), // Generowanie UUID
                    name: values.name,
                    category_name: dictCategories.find((category: DictCategories) => category.id === values.categoryId)?.name || '',
                    category_id: values.categoryId,
                    dict: "PER_USER",
                    dict_id: crypto.randomUUID(), // Generowanie UUID
                    category_type: 'PER_USER'
                };
                onAddExercise(newExercise);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
        } else {
            console.log("Nazwa już istnieje:", values.name);
        }
    };

    const initialValues: { name: string, categoryId: number } = {
        name: '',
        categoryId: 1,
    }

    return (
        <div>
            <Formik
                initialValues={initialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .required(t('validation.required'))
                        .notOneOf(dictExercises.map(exercise => exercise.name), t('validation.unique'))
                })}
                onSubmit={(values, { setSubmitting }) => {
                    postDictExercises(values);
                    setSubmitting(false);
                }}
            >
                {formik => (
                    <Form>
                        <div>
                            <label htmlFor="language">{t('form.language')}</label>
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
                        <Field name="name" type="text" />
                        <ErrorMessage name="name" component="div" className="error" />
                        <Field as="select" name="categoryId">
                            <option value="" disabled>{t("table.category")}</option>
                            {dictCategories.map((category: DictCategories) => (
                                <option key={category.id} value={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </Field>
                        <button type="submit">{t('buttons.add')}</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default withTranslation("global")(AddDictExercisePerUser);
