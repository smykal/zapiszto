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
  confirmPassword: string,
  loading: boolean,
  message: string
};

const ResetPassword: React.FC<Props> = ({ t }) => {
  const [searchParams] = useSearchParams();
  const [message, setMessage] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const validationSchema = () => {
    return Yup.object().shape({
      newPassword: Yup.string()
        .required(t("reset_password.new_password_required")),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref("newPassword"), null], t("reset_password.passwords_must_match"))
        .required(t("reset_password.confirm_password_required")),
    });
  };

  const handleResetPassword = (formValue: { newPassword: string; confirmPassword: string }) => {
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
    confirmPassword: "",
  };

  return (
    <div>
      <div>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleResetPassword}
        >
          <Form>
            <div className="card card-container">
              <h2>{t("login.reset_password")}</h2>
              <div className="field-container">
                <label htmlFor="newPassword" className="field-label">
                  <img src="/images/login/password.png" alt="password-img" />
                </label>
                <div className="field-input">
                  <Field name="newPassword" type="password" placeholder={t("login.password")} className="form-field" />
                  <ErrorMessage name="newPassword" component="div" className="alert alert-danger" />
                </div>

              </div>

              <div className="field-container">
                <label htmlFor="confirmPassword" className="field-label">
                  <img src="/images/login/password.png" alt="password-img" />
                </label>
                <div className="field-input">
                  <Field name="confirmPassword" type="password" placeholder={t("login.repeat_password")} className="form-field" />
                  <ErrorMessage name="confirmPassword" component="div" className="alert alert-danger" />
                </div>
              </div>

              <div >
                <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                  {loading && <span className="spinner-border spinner-border-sm"></span>}
                  <span>{t("reset_password.reset")}</span>
                </button>
              </div>

              {message && (
                <div className="field-container">
                  <div className="alert alert-danger" role="alert">
                    {message}
                  </div>
                </div>
              )}

            </div>
          </Form>
        </Formik>
      </div>
    </div>
  );
};

export default withTranslation("global")(ResetPassword);
