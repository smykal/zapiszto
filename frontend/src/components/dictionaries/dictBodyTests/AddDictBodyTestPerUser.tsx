import { Component } from "react";
import { DictBodyTest, NewDictBodyTest } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/dict/DictBodyTestService';
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";


type Props = {
    dictBodyTest: DictBodyTest[];
    t: any
};
type State = {};

class AddDictBodyTestPerUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {};
    }

    postDictBodyTest = (values: { newDictBodyTestName: string, newDictBodyTestDescription: string, newDictBodyTestPurpose: string }) => {
        const { dictBodyTest } = this.props;
        const isNameExisting = dictBodyTest.some(BodyTest => BodyTest.name === values.newDictBodyTestName);
        if (!isNameExisting) {
            const newDictBodyTest: NewDictBodyTest = {
                name: values.newDictBodyTestName,
                description: values.newDictBodyTestDescription,
                purpose: values.newDictBodyTestPurpose
            };
            Service.postDictBodyTestPerUser(newDictBodyTest)
                .then(() => {
                    console.log("Wysłano nowy typ ćwiczeń:", values.newDictBodyTestName);
                    window.location.reload();
                })
                .catch(error => {
                    console.error("Błąd podczas wysyłania zapytania:", error);
                });
        } else {
            console.log("Nazwa już istnieje:", values.newDictBodyTestName);
        }
    }

    render() {
        const { dictBodyTest, t } = this.props;
        return (
            <div>
                <Formik
                    initialValues={{
                        newDictBodyTestName: '',
                        newDictBodyTestDescription: '',
                        newDictBodyTestPurpose: ''
                    }}
                    validationSchema={Yup.object({
                        newDictBodyTestName: Yup.string()
                            .required(t("validation.this_field_is_required"))
                            .notOneOf(dictBodyTest.map(item => item.name), t("validation.name_already_exists")),
                        newDictBodyTestDescription: Yup.string()
                            .required(t("validation.this_field_is_required")),
                        newDictBodyTestPurpose: Yup.string()
                            .required(t("validation.this_field_is_required"))
                    })}
                    onSubmit={(values, { setSubmitting }) => {
                        this.postDictBodyTest(values);
                        setSubmitting(false);
                    }}
                >
                    {formik => (
                        <Form>
                            <Field name="newDictBodyTestName" type="text" className="form-control" placeholder={t("table.name")} />
                            <ErrorMessage name="newDictBodyTestName" component="div" className="error" />

                            <Field name="newDictBodyTestDescription" type="text" className="form-control" placeholder={t("table.description")} />
                            <ErrorMessage name="newDictBodyTestDescription" component="div" className="error" />

                            <Field name="newDictBodyTestPurpose" type="text" className="form-control" placeholder={t("table.purpose")} />
                            <ErrorMessage name="newDictBodyTestPurpose" component="div" className="error" />

                            <button type="submit" disabled={formik.isSubmitting}>{t("buttons.add")}</button>
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}

export default withTranslation("global")(AddDictBodyTestPerUser);