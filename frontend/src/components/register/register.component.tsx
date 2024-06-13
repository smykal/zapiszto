import React, { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { withTranslation } from "react-i18next";
import AuthService from "../../services/auth.service";

type Props = {
  t: any;
};

type State = {
  username: string;
  email: string;
  password: string;
  successful: boolean;
  message: string;
  role: string[];
  termsAccepted: boolean;
  privacyAccepted: boolean;
};

class Register extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);

    this.state = {
      username: "",
      email: "",
      password: "",
      successful: false,
      message: "",
      role: [],
      termsAccepted: false,
      privacyAccepted: false,
    };
  }

  validationSchema() {
    return Yup.object().shape({
      username: Yup.string()
        .test(
          "len",
          "The username must be between 3 and 20 characters.",
          (val: any) =>
            val && val.toString().length >= 3 && val.toString().length <= 20
        )
        .required("This field is required!"),
      email: Yup.string()
        .email("This is not a valid email.")
        .required("This field is required!"),
      password: Yup.string()
        .test(
          "len",
          "The password must be between 6 and 40 characters.",
          (val: any) =>
            val && val.toString().length >= 6 && val.toString().length <= 40
        )
        .required("This field is required!"),
      role: Yup.string().required("This field is required!"),
      termsAccepted: Yup.boolean().oneOf([true], "You must accept the terms and conditions"),
      privacyAccepted: Yup.boolean().oneOf([true], "You must accept the privacy policy"),
    });
  }

  handleRegister(formValue: { username: string; email: string; password: string; role: string; termsAccepted: boolean; privacyAccepted: boolean }) {
    const { username, email, password, role, termsAccepted, privacyAccepted } = formValue;

    this.setState({
      message: "",
      successful: false,
    });

    AuthService.register(username, email, password, role).then(
      (response) => {
        this.setState({
          message: response.data.message,
          successful: true,
        });
      },
      (error) => {
        const resMessage =
          (error.response && error.response.data && error.response.data.message) ||
          error.message ||
          error.toString();

        this.setState({
          successful: false,
          message: resMessage,
        });
      }
    );
  }

  render() {
    const { successful, message } = this.state;
    const { t } = this.props;

    const initialValues = {
      username: "",
      email: "",
      password: "",
      role: "ROLE_USER",
      termsAccepted: false,
      privacyAccepted: false,
    };

    return (
      <div className="col-md-12">
        <div className="card card-container">
          <img
            src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            alt="profile-img"
            className="profile-img-card"
          />

          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema()}
            onSubmit={this.handleRegister}
          >
            <Form>
              {!successful && (
                <div>
                  <div className="form-group">
                    <label htmlFor="username"> {t("login.username")} </label>
                    <Field name="username" type="text" className="form-control" />
                    <ErrorMessage
                      name="username"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div className="form-group">
                    <label htmlFor="email"> {t("login.email")} </label>
                    <Field name="email" type="email" className="form-control" />
                    <ErrorMessage
                      name="email"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div className="form-group">
                    <label htmlFor="password"> {t("login.password")} </label>
                    <Field name="password" type="password" className="form-control" />
                    <ErrorMessage
                      name="password"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="role user">
                      <Field type="radio" name="role" value="user" /> ROLE_USER
                    </label>
                    <label htmlFor="role trener">
                      <Field type="radio" name="role" value="trainer" /> ROLE_TRENER
                    </label>
                    <label htmlFor="role admin">
                      <Field type="radio" name="role" value="admin" /> ROLE_ADMIN
                    </label>
                  </div>
                  <div className="form-group">
                    <label>
                      <Field type="checkbox" name="termsAccepted" />
                      {t("login.terms_accepted")}
                    </label>
                    <ErrorMessage
                      name="termsAccepted"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <label>
                      <Field type="checkbox" name="privacyAccepted" />
                      {t("login.privacy_accepted")}
                    </label>
                    <ErrorMessage
                      name="privacyAccepted"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <button type="submit" className="btn btn-primary btn-block">{t("login.register")}</button>
                  </div>
                </div>
              )}

              {message && (
                <div className="form-group">
                  <div
                    className={
                      successful ? "alert alert-success" : "alert alert-danger"
                    }
                    role="alert"
                  >
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

export default withTranslation("global")(Register);
