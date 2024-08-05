import React, { useState } from "react";
import { DictBodyTest, NewDictBodyTest } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/dict/DictBodyTestService';
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";
import { LANGUAGES, Language } from "../../../translations/Languages";

type Props = {
    dictBodyTest: DictBodyTest[];
    onAddBodyTest: (newBodyTest: DictBodyTest) => void;
    t: any;
};
type State = {};

const AddDictBodyTestPerUser: React.FC<Props> = ({ dictBodyTest, onAddBodyTest, t }) => {
    const [selectedLanguage, setSelectedLanguage] = useState("en");

    const postDictBodyTest = (values: { newDictBodyTestName: string, newDictBodyTestDescription: string, newDictBodyTestPurpose: string }) => {
        const isNameExisting = dictBodyTest.some(BodyTest => BodyTest.name === values.newDictBodyTestName);
        if (!isNameExisting) {
            const newDictBodyTest: NewDictBodyTest = {
                name: { [selectedLanguage]: values.newDictBodyTestName },
                description: { [selectedLanguage]: values.newDictBodyTestDescription },
                purpose: { [selectedLanguage]: values.newDictBodyTestPurpose }
            };
            Service.postDictBodyTestPerUser(newDictBodyTest)
                .then(response => {
                    const addedBodyTest: DictBodyTest = {
                        id: crypto.randomUUID(),
                        dict: "PER_USER",
                        dict_id: crypto.randomUUID(),
                        name: values.newDictBodyTestName,
                        description: values.newDictBodyTestDescription,
                        purpose: values.newDictBodyTestPurpose
                    };
                    onAddBodyTest(addedBodyTest);
                })
                .catch(error => {
                    console.error("Błąd podczas wysyłania zapytania:", error);
                });
        } else {
            console.log("Nazwa już istnieje:", values.newDictBodyTestName);
        }
    };

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
                        .required(t("validation.this_field_is_required")),
                    newDictBodyTestDescription: Yup.string()
                        .required(t("validation.this_field_is_required")),
                    newDictBodyTestPurpose: Yup.string()
                        .required(t("validation.this_field_is_required"))
                })}
                onSubmit={(values, { setSubmitting }) => {
                    postDictBodyTest(values);
                    setSubmitting(false);
                }}
            >
                {formik => (
                    <Form>
                        <div>
                            <label htmlFor="language">Language</label>
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
};

export default withTranslation("global")(AddDictBodyTestPerUser);
