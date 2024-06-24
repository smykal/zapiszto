import React, { Component } from "react";
import { DictCategories, NewDictCategory } from "../../../types/types";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Service from '../../../services/exercises/';
import * as Yup from 'yup';
import { withTranslation } from "react-i18next";

type Props = {
  dictCategory: DictCategories[];
  onAddCategory: (newCategory: DictCategories) => void;
  t: any;
};
type State = {};

class AddDictCategoryPerUser extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {};
  }

  postDictCategory = (values: { newDictCategoryName: string, newDictCategoryDescription: string }) => {
    const { dictCategory, onAddCategory } = this.props;
    const isNameExisting = dictCategory.some(category => category.name === values.newDictCategoryName);
    if (!isNameExisting) {
      const newDictCategory: NewDictCategory = {
        name: values.newDictCategoryName,
        description: values.newDictCategoryDescription
      };
      Service.postDictCategoryPerUser(newDictCategory)
        .then(response => {
          const addedCategory = {
            ...newDictCategory,
            dict: "PER_USER",
            id: Math.max(...dictCategory.map(c => c.id)) + 1,
            dict_id: Math.max(...dictCategory.map(c => c.dict_id)) + 1
          };
          onAddCategory(addedCategory);
        })
        .catch(error => {
          console.error('Error adding category:', error);
        });
    } else {
      console.log("Nazwa już istnieje:", values.newDictCategoryName);
    }
  }

  render() {
    const { dictCategory, t } = this.props;
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
            setTimeout(() => {
              this.postDictCategory(values);
              setSubmitting(false);
            }, 400);
          }}
        >
          {formik => (
            <Form>
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
}

export default withTranslation("global")(AddDictCategoryPerUser);
