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
      <div className="col-md-12">
        <div className="card card-container">
          <h2>{t("recover_password.title")}</h2>
          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema}
            onSubmit={this.handleRecoverPassword}
          >
            <Form>
              <div className="form-group">
                <label htmlFor="email">{t("recover_password.email")}</label>
                <Field name="email" type="email" className="form-control" />
                <ErrorMessage name="email" component="div" className="alert alert-danger" />
              </div>

              <div className="form-group">
                <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                  {loading && <span className="spinner-border spinner-border-sm"></span>}
                  <span>{t("recover_password.send_link")}</span>
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
  }
}

export default withTranslation("global")(RecoverPassword);
