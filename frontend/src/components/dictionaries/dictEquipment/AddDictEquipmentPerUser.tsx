import React, { Component } from "react";
import { DictEquipment, NewDictEquipment } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import DictEquipmentService from '../../../services/dict/DictEquipmentService';
import * as Yup from 'yup';
import { format } from "path";

type Props = {
    dictEquipment: DictEquipment[];
    onAddEquipment: (newEquipment: DictEquipment) => void;
};

type State = {};

export default class AddDictEquipmentPerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictEquipment = (values: { newDictEquipmentName: string }) => {
        const { dictEquipment, onAddEquipment } = this.props;
        const isNameExisting = dictEquipment.some(item => item.name === values.newDictEquipmentName);
        if (!isNameExisting) {
            const newDictEquipment: NewDictEquipment = {
                id: crypto.randomUUID(),
                name: values.newDictEquipmentName,
            };
            DictEquipmentService.postDictEquipmentPerUser(newDictEquipment)
                .then(response => {
                    const addedEquipment = {
                        ...newDictEquipment,
                        dict: "PER_USER",
                        dict_id: crypto.randomUUID(), // Simulate the new dict_id or get from response
                    };
                    onAddEquipment(addedEquipment);
                })
                .catch(error => {
                    console.error('Error sending the request:', error);
                });
        } else {
            console.log("Name already exists:", values.newDictEquipmentName);
        }
    }

    render() {
        const { dictEquipment } = this.props;
        return (
            <div>
                <Formik
                    initialValues={{
                        newDictEquipmentName: '',
                    }}
                    validationSchema={Yup.object({
                        newDictEquipmentName: Yup.string()
                            .required('Pole jest wymagane')
                            .notOneOf(dictEquipment.map(item => item.name), 'Nazwa już istnieje'),
                    })}
                    onSubmit={(values, { setSubmitting }) => {
                        this.postDictEquipment(values);
                        setSubmitting(false);
                    }}
                >
                    {formik => (
                        <Form>
                            <Field name="newDictEquipmentName" type="text" className="form-control" />
                            <ErrorMessage name="newDictEquipmentName" component="div" className="error" />
                            <button type="submit" disabled={formik.isSubmitting}>Add</button>
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}
