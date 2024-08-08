import React, { useState } from "react";
import { DictCategories, NewDictCategory } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/dict/DictCategoryService';
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";
import { LANGUAGES } from "../../../translations/Languages";

type Props = {
    dictCategory: DictCategories[];
    onAddCategory: (newCategory: DictCategories) => void;
    t: any;
};

const AddDictCategoryPerUser: React.FC<Props> = ({ dictCategory, onAddCategory, t }) => {
    const [selectedLanguage, setSelectedLanguage] = useState("en");

    const postDictCategory = (values: { newDictCategoryName: string, newDictCategoryDescription: string }) => {
        const newDictCategory: NewDictCategory = {
            name: { [selectedLanguage]: values.newDictCategoryName },
            description: { [selectedLanguage]: values.newDictCategoryDescription }
        };
        Service.postDictCategoryPerUser(newDictCategory)
            .then(response => {
                const addedCategory: DictCategories = {
                    id: Math.max(...dictCategory.map(c => c.id)) + 1,
                    dict: "PER_USER",
                    dict_id: Math.max(...dictCategory.map(c => c.dict_id)) + 1,
                    name: values.newDictCategoryName,
                    description: values.newDictCategoryDescription
                };
                onAddCategory(addedCategory);
            })
            .catch(error => {
                console.error('Error adding category:', error);
            });
    };

    return (
        <div>
            <Formik
                initialValues={{
                    newDictCategoryName: '',
                    newDictCategoryDescription: ''
                }}
                validationSchema={Yup.object({
                    newDictCategoryName: Yup.string()
                        .required(t("validation.this_field_is_required"))
                        .notOneOf(dictCategory.map(item => item.name), 'Nazwa już istnieje'),
                    newDictCategoryDescription: Yup.string()
                        .required(t("validation.this_field_is_required"))
                })}
                onSubmit={(values, { setSubmitting }) => {
                    postDictCategory(values);
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
                        <Field name="newDictCategoryName" type="text" className="form-control" />
                        <ErrorMessage name="newDictCategoryName" component="div" className="error" />
                        <Field name="newDictCategoryDescription" type="text" className="form-control" />
                        <ErrorMessage name="newDictCategoryDescription" component="div" className="error" />
                        <button type="submit" disabled={formik.isSubmitting}>{t("buttons.add")}</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
}

export default withTranslation("global")(AddDictCategoryPerUser);
