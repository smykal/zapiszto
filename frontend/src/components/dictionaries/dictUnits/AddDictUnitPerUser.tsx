import { Component } from "react";
import { DictUnits, NewDictUnit } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';

type Props = {
    dictUnit: DictUnits[];
};
type State = {};

export default class AddDictUnitPerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictUnit(values: { newDictUnitName: string, newDictUnitShortcut: string }) {
        const { dictUnit: dictUnit } = this.props;
        const isNameExisting = dictUnit.some(item => item.name === values.newDictUnitName);
        if (!isNameExisting) {
            const newDictUnit: NewDictUnit = {
                name: values.newDictUnitName,
                shortcut: values.newDictUnitShortcut
            };
            Service.postDictUnitPerUser(newDictUnit);
            console.log("Wysłano nowy typ jednostki:", values.newDictUnitName);
            window.location.reload();
        } else {
            console.log("Nazwa już istnieje:", values.newDictUnitName);
        }
    }

    render() {
        const { dictUnit: dictUnit } = this.props;
        return (
            <div>
                <Formik
                    initialValues={{
                        newDictUnitName: 'name',
                        newDictUnitShortcut: 'shortcut'
                    }}
                    validationSchema={Yup.object({
                        newDictUnitName: Yup.string()
                            .required('Pole jest wymagane')
                            .notOneOf(dictUnit.map(item => item.name), 'Nazwa już istnieje'),
                        newDictUnitShortcut: Yup.string()
                            .required('Pole jest wymagane')
                    })}
                    onSubmit={(values, { setSubmitting }) => {
                        setTimeout(() => {
                            this.postDictUnit(values);
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {formik => (
                        <Form>
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
    }
}