import { Component } from "react";
import { DictQuantityType, NewDictQuantityType } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';

type Props = {
    dictQuantityType: DictQuantityType[];
};
type State = {};

export default class AddDictQuantityTypePerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictQuantityType(values: { newDictQuantityTypeName: string, newDictQuantityTypeShortcut: string }) {
        const { dictQuantityType } = this.props;
        const isNameExisting = dictQuantityType.some(quantityType => quantityType.name === values.newDictQuantityTypeName);
        if (!isNameExisting) {
            const newDictQuantityType: NewDictQuantityType = {
                name: values.newDictQuantityTypeName,
                shortcut: values.newDictQuantityTypeShortcut
            };
            // Zakładając, że funkcja postDictQuantityTypePerUser przyjmuje tylko jeden argument - obiekt nowego typu ćwiczeń.
            Service.postDictQuantityTypePerUser(newDictQuantityType);
            console.log("Wysłano nowy typ ćwiczeń:", values.newDictQuantityTypeName);
        } else {
            console.log("Nazwa już istnieje:", values.newDictQuantityTypeName);
        }
    }

    render() {
        const { dictQuantityType } = this.props;
        return (
            <div>
                <Formik
                    initialValues={{
                        newDictQuantityTypeName: 'name',
                        newDictQuantityTypeShortcut: 'shortcut'
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