import React, { useState } from "react";
import { useSearchParams } from "react-router-dom";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { withTranslation } from "react-i18next";
import AuthService from "../../services/auth.service";

type Props = {
  t: any;
};

type State = {
  newPassword: string,
  loading: boolean,
  message: string
};

const ResetPassword: React.FC<Props> = ({ t }) => {
  const [searchParams] = useSearchParams();
  const [message, setMessage] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const validationSchema = () => {
    return Yup.object().shape({
      newPassword: Yup.string().required("This field is required!"),
    });
  };

  const handleResetPassword = (formValue: { newPassword: string }) => {
    const { newPassword } = formValue;
    const token = searchParams.get("token") || "";

    setLoading(true);

    AuthService.resetPassword(token, newPassword).then(
      (response) => {
        setMessage(response.data.message);
        setLoading(false);
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setLoading(false);
        setMessage(resMessage);
      }
    );
  };

  const initialValues = {
    newPassword: "",
  };

  return (
    <div className="col-md-12">
      <div className="card card-container">
        <h2>{t("reset_password.title")}</h2>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleResetPassword}
        >
          <Form>
            <div className="form-group">
              <label htmlFor="newPassword">{t("reset_password.new_password")}</label>
              <Field name="newPassword" type="password" className="form-control" />
              <ErrorMessage name="newPassword" component="div" className="alert alert-danger" />
            </div>

            <div className="form-group">
              <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                {loading && <span className="spinner-border spinner-border-sm"></span>}
                <span>{t("reset_password.reset")}</span>
              </button>
            </div>

            {message && (
              <div className="form-group">
                <div className="alert alert-danger" role="alert">
                  {message}
                </div>
              </div>
            )}
          </Form>
        </Formik>
      </div>
    </div>
  );
};

export default withTranslation("global")(ResetPassword);
