import React, { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import IUser from "../../types/user.type";
import { Navigate } from "react-router-dom";
import * as Yup from "yup";
import { withTranslation } from "react-i18next";
import AuthService from "../../services/auth.service";
import RecoverPassword from "./RecoverPassword";

type Props = {
  t: any;
  updateUserState: (user: IUser) => void;
};

type State = {
  redirect: string | null,
  username: string,
  password: string,
  loading: boolean,
  message: string,
  showRecoverPassword: boolean
};

class Login extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);
    this.handleShowRecoverPassword = this.handleShowRecoverPassword.bind(this);

    this.state = {
      redirect: null,
      username: "",
      password: "",
      loading: false,
      message: "",
      showRecoverPassword: false
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (currentUser) {
      this.setState({ redirect: "/profile" });
    }
  }

  validationSchema() {
    return Yup.object().shape({
      username: Yup.string().required("This field is required!"),
      password: Yup.string().required("This field is required!"),
    });
  }

  handleLogin(formValue: { username: string; password: string }) {
    const { username, password } = formValue;
    const { updateUserState } = this.props;

    this.setState({
      message: "",
      loading: true
    });

    AuthService.login(username, password).then(
      (data) => {
        updateUserState(data);
        this.setState({
          redirect: "/profile"
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

  handleShowRecoverPassword() {
    this.setState({
      showRecoverPassword: true
    });
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />;
    }

    const { t } = this.props;
    const { loading, message, showRecoverPassword } = this.state;

    const initialValues = {
      username: "",
      password: "",
    };

    if (showRecoverPassword) {
      return <RecoverPassword />;
    }

    return (
      <div>
        <div className="card card-container">
          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema()}
            onSubmit={this.handleLogin}
          >
            <Form>
              <div className="field-container">
                <label htmlFor="username" className="field-label">
                  <img src="/images/login/user.png" alt="username-img" />
                </label>
                <div className="field-input">
                  <Field name="username" type="text" />
                  <ErrorMessage name="username" component="div" />
                </div>
              </div>

              <div className="field-container">
                <label htmlFor="password" className="field-label">
                  <img src="/images/login/password.png" alt="password-img" />
                </label>
                <div className="field-input">
                  <Field name="password" type="password" />
                  <ErrorMessage name="password" component="div" />
                </div>
              </div>

              <div className="centered-button">
                <div>
                  <button type="submit" disabled={loading}>
                    {loading && (
                      <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>{t("login.login")}</span>
                  </button>
                </div>

                <div>
                  <button
                    type="button"
                    onClick={this.handleShowRecoverPassword}
                  >
                    {t("login.forgot_password")}
                  </button>
                </div>
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

export default withTranslation("global")(Login);