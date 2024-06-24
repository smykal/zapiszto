import React, { Component } from "react";
import { DictUnits, NewDictUnit } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';

type Props = {
    dictUnits: DictUnits[];
    onAddUnit: (newUnit: DictUnits) => void;
};
type State = {};

export default class AddDictUnitPerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictUnit = (values: { newDictUnitName: string, newDictUnitShortcut: string }) => {
        const { dictUnits, onAddUnit } = this.props;
        const isNameExisting = dictUnits.some(item => item.name === values.newDictUnitName);
        if (!isNameExisting) {
            const newDictUnit: NewDictUnit = {
                name: values.newDictUnitName,
                shortcut: values.newDictUnitShortcut
            };
            Service.postDictUnitPerUser(newDictUnit)
                .then(response => {
                    const addedUnit = {
                        ...newDictUnit,
                        id: Math.max(...dictUnits.map(u => u.id)) + 1, // Simulate the new ID
                        dict: "PER_USER",
                        dict_id: Math.max(...dictUnits.map(u => u.dict_id)) + 1 // Simulate the new dict_id
                    };
                    onAddUnit(addedUnit);
                })
                .catch(error => {
                    console.error('Error sending the request:', error);
                });
        } else {
            console.log("Name already exists:", values.newDictUnitName);
        }
    }

    render() {
        const { dictUnits } = this.props;
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
                            .notOneOf(dictUnits.map(item => item.name), 'Nazwa już istnieje'),
                        newDictUnitShortcut: Yup.string()
                            .required('Pole jest wymagane')
                    })}
                    onSubmit={(values, { setSubmitting }) => {
                        this.postDictUnit(values);
                        setSubmitting(false);
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
