import React from "react";
import { DictExercises, NewDictExercises } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/'
import * as Yup from 'yup';
import { useTranslation } from "react-i18next";

type Props = {
    dictExercises: DictExercises[];
};

const AddDictExercisePerUser: React.FC<Props> = ({ dictExercises }) => {
    const { t } = useTranslation("global");

    const postDictExercises = (values: { newDictExercise: string }) => {
        const isNameExisting = dictExercises.some(exercise => exercise.name === values.newDictExercise);
        if (!isNameExisting) {
            const newDictExercise: NewDictExercises = { name: values.newDictExercise }
            Service.postDictExercisePerUser(newDictExercise)
            console.log("Wysłano nowe ćwiczenie:", values.newDictExercise);
            window.location.reload();
        } else {
            console.log("Nazwa już istnieje:", values.newDictExercise);
        }
    };

    return (
        <div>
            <Formik
                initialValues={{ newDictExercise: '' }}
                validationSchema={Yup.object({
                    newDictExercise: Yup.string()
                        .required(t('validation.required'))
                        .notOneOf(dictExercises.map(exercise => exercise.name), t('validation.unique'))
                })}
                onSubmit={(values, { setSubmitting }) => {
                    setTimeout(() => {
                        postDictExercises(values);
                        setSubmitting(false);
                    }, 400);
                }}
            >
                {formik => (
                    <Form>
                        <Field name="newDictExercise" type="text" className="form-control" />
                        <ErrorMessage name="newDictExercise" component="div" className="error" />
                        <button type="submit" disabled={formik.isSubmitting}>{t('buttons.add')}</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddDictExercisePerUser;
