import { Component } from "react";
import { DictExercises, NewDictExercises } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/'
import * as Yup from 'yup';

type Props = {
    dictExercises: DictExercises[];
};
type State = {};

export default class AddDictExercisePerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictExercises(values: { newDictExercise: string }) {
        const { dictExercises } = this.props;
        const isNameExisting = dictExercises.some(exercise => exercise.name === values.newDictExercise);
        if (!isNameExisting) {
            const newDictExercise: NewDictExercises = { name: values.newDictExercise }
            Service.postDictExercisePerUser(newDictExercise)
            console.log("Wysłano nowe ćwiczenie:", values.newDictExercise);
        } else {
            console.log("Nazwa już istnieje:", values.newDictExercise);
        }
    }

    render() {
        const { dictExercises } = this.props;
        return (
            <div>
                <Formik
                    initialValues={{ newDictExercise: '' }}
                    validationSchema={Yup.object({
                        newDictExercise: Yup.string()
                            .required('Pole jest wymagane')
                            .notOneOf(dictExercises.map(exercise => exercise.name), 'Nazwa już istnieje')
                    })}
                    onSubmit={(values, { setSubmitting }) => {
                        setTimeout(() => {
                            this.postDictExercises(values);
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {formik => (
                        <Form>
                            <Field name="newDictExercise" type="text" className="form-control" />
                            <ErrorMessage name="newDictExercise" component="div" className="error" />
                            <button type="submit" disabled={formik.isSubmitting}>Add</button>
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}
