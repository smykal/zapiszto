import React, { Component } from "react";
import { DictCategories, DictExercises, NewDictExercises } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/'
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";

type Props = {
    dictExercises: DictExercises[];
    dictCategories: DictCategories[];
    t: any;
};

type State = {
    newDictExercise: string;
    categoryId: number | null;
};

class AddDictExercisePerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            newDictExercise: '',
            categoryId: null
        };
    }

    postDictExercises = (values: NewDictExercises) => {
        const { dictExercises } = this.props;
        const { name, categoryId } = values;
        const isNameExisting = dictExercises.some(exercise => exercise.name === name);
        if (!isNameExisting) {
            const newDictExerciseData: NewDictExercises = { 
                name: name,
                categoryId: categoryId || 1
            };
            Service.postDictExercisePerUser(newDictExerciseData)
            .then(() => {
                console.log("Wysłano nowe ćwiczenie:", name);
                window.location.reload();
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
        } else {
            console.log("Nazwa już istnieje:", name);
        }
    };
    

    render() {
        const { dictCategories, t } = this.props;

        const initialValues: NewDictExercises = {
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
                            .notOneOf(this.props.dictExercises.map(exercise => exercise.name), t('validation.unique'))
                    })}
                    onSubmit={this.postDictExercises}
                >
                    {formik => (
                        <Form>
                            <Field name="name" type="text" />
                            <ErrorMessage name="name" component="div" className="error" />
                            <Field as="select" name="categoryId">
                                <option value="" disabled>{t("table.category")}</option>
                                {dictCategories.map((category) => (
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
    }
}

export default withTranslation("global")(AddDictExercisePerUser);
