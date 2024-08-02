import React, { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { withTranslation } from "react-i18next";
import AuthService from "../../services/auth.service";

type Props = {
  t: any;
};

type State = {
  email: string,
  loading: boolean,
  message: string
};

class RecoverPassword extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRecoverPassword = this.handleRecoverPassword.bind(this);

    this.state = {
      email: "",
      loading: false,
      message: ""
    };
  }

  validationSchema() {
    return Yup.object().shape({
      email: Yup.string().email("Invalid email format").required("This field is required!")
    });
  }

  handleRecoverPassword(formValue: { email: string }) {
    const { email } = formValue;

    this.setState({
      message: "",
      loading: true
    });

    AuthService.forgotPassword(email).then(
      response => {
        this.setState({
          message: response.data.message,
          loading: false
        });
      },
      error => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        this.setState({
          loading: false,
          message: resMessage
        });
      }
    );
  }

  render() {
    const { t } = this.props;
    const { loading, message } = this.state;

    const initialValues = {
      email: "",
    };

    return (
      <div>
        <div >
          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema}
            onSubmit={this.handleRecoverPassword}
          >
            <Form>
              <div className="card card-container">
                <h2>{t("login.recover_password")}</h2>
                <div className="field-container">
                  <label htmlFor="email" className="field-label">
                    <img src="/images/login/email.png" alt="email-img" />
                  </label>
                  <div className="field-input">
                    <Field name="email" type="email" placeholder={t("login.email")} className="form-field" />
                    <ErrorMessage name="email" component="div" className="alert alert-danger" />
                  </div>

                </div>

                <div>
                  <button type="submit" disabled={loading}>
                    {loading && <span className="spinner-border spinner-border-sm"></span>}
                    <span>{t("login.send_link")}</span>
                  </button>
                </div>

                {message && (
                  <div className="form-group">
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
  }
}

export default withTranslation("global")(RecoverPassword);
