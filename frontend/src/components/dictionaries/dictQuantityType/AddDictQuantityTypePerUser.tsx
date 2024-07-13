import React, { Component } from "react";
import { DictQuantityType, NewDictQuantityType } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';

type Props = {
    dictQuantityType: DictQuantityType[];
    onAddQuantityType: (newQuantityType: DictQuantityType) => void;
};

class AddDictQuantityTypePerUser extends Component<Props> {
    postDictQuantityType = (values: { newDictQuantityTypeName: string, newDictQuantityTypeShortcut: string }) => {
        const { dictQuantityType, onAddQuantityType } = this.props;
        const isNameExisting = dictQuantityType.some(quantityType => quantityType.name === values.newDictQuantityTypeName);
        if (!isNameExisting) {
            const newDictQuantityType: NewDictQuantityType = {
                id: crypto.randomUUID(), // Generate a new UUID
                name: values.newDictQuantityTypeName,
                shortcut: values.newDictQuantityTypeShortcut
            };
            Service.postDictQuantityTypePerUser(newDictQuantityType)
            .then(() => {
                console.log("Wysłano nowy typ ilości:", values.newDictQuantityTypeName);
                // Simulate the server response as the service method does not return data
                const newQuantityType: DictQuantityType = {
                    ...newDictQuantityType,
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

    render() {
        const { dictQuantityType } = this.props;

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
                            this.postDictQuantityType(values);
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {formik => (
                        <Form>
                            <Field name="newDictQuantityTypeName" type="text" className="form-control" />
                            <ErrorMessage name="newDictQuantityTypeName" component="div" className="error" />
                            
                            <Field name="newDictQuantityTypeShortcut" type="text" className="form-control" />
                            <ErrorMessage name="newDictQuantityTypeShortcut" component="div" className="error" />

                            <button type="submit" disabled={formik.isSubmitting}>Add</button>
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}

export default AddDictQuantityTypePerUser;
