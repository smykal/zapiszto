import React, { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { withTranslation } from "react-i18next";
import AuthService from "../../services/auth.service";
import ReCAPTCHA from "react-google-recaptcha";
import { RECAPTCHA_SITE_KEY } from "../../constants/api";
import Modal from "../../constants/Modal";
import Terms from "../../constants/Terms";
import Privacy from "../../constants/Privacy";



type Props = {
  t: any;
};

type State = {
  username: string;
  email: string;
  password: string;
  repeatPassword: string;
  successful: boolean;
  message: string;
  role: string[];
  termsAccepted: boolean;
  privacyAccepted: boolean;
  recaptchaToken: string | null;
  showTermsModal: boolean;
  showPrivacyModal: boolean;
};

class Register extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.onRecaptchaChange = this.onRecaptchaChange.bind(this);
    this.showTermsModal = this.showTermsModal.bind(this);
    this.hideTermsModal = this.hideTermsModal.bind(this);
    this.showPrivacyModal = this.showPrivacyModal.bind(this);
    this.hidePrivacyModal = this.hidePrivacyModal.bind(this);

    this.state = {
      username: "",
      email: "",
      password: "",
      repeatPassword: "",
      successful: false,
      message: "",
      role: [],
      termsAccepted: false,
      privacyAccepted: false,
      recaptchaToken: null,
      showTermsModal: false,
      showPrivacyModal: false,
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
      repeatPassword: Yup.string()
        .oneOf([Yup.ref('password'), null], 'Passwords must match')
        .required("This field is required!"),
      role: Yup.string().required("This field is required!"),
      termsAccepted: Yup.boolean().oneOf([true], "You must accept the terms and conditions"),
      privacyAccepted: Yup.boolean().oneOf([true], "You must accept the privacy policy"),
      recaptchaToken: Yup.string().required("Please complete the reCAPTCHA")
    });
  }

  onRecaptchaChange(token: string | null) {
    this.setState({ recaptchaToken: token });
  }

  handleRegister(formValue: { username: string; email: string; password: string; repeatPassword: string; role: string; termsAccepted: boolean; privacyAccepted: boolean; recaptchaToken: string | null }) {
    const { username, email, password, role } = formValue;

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

  showTermsModal() {
    this.setState({ showTermsModal: true });
  }

  hideTermsModal() {
    this.setState({ showTermsModal: false });
  }

  showPrivacyModal() {
    this.setState({ showPrivacyModal: true });
  }

  hidePrivacyModal() {
    this.setState({ showPrivacyModal: false });
  }

  render() {
    const { successful, message, showTermsModal, showPrivacyModal } = this.state;
    const { t } = this.props;

    const initialValues = {
      username: "",
      email: "",
      password: "",
      repeatPassword: "",
      role: "ROLE_USER",
      termsAccepted: false,
      privacyAccepted: false,
      recaptchaToken: null,
    };

    return (
      <div>
        <Formik
          initialValues={initialValues}
          validationSchema={this.validationSchema()}
          onSubmit={this.handleRegister}
        >
          {({ setFieldValue }) => (
            <Form>
              {!successful && (
                <div className="card card-container">
                  <div className="field-container">
                    <label htmlFor="username" className="field-label">
                      <img src="/images/login/user.png" alt="username-img" />
                    </label>
                    <div className="field-input">
                      <Field name="username" type="text" placeholder={t("login.username")} className="form-field" />
                      <ErrorMessage
                        name="username"
                        component="div"
                        className="alert alert-danger"
                      />
                    </div>
                  </div>

                  <div className="field-container">
                    <label htmlFor="email" className="field-label">
                      <img src="/images/login/email.png" alt="email-img" />
                    </label>
                    <div className="field-input">
                      <Field name="email" type="email" placeholder={t("login.email")} className="form-field" />
                      <ErrorMessage
                        name="email"
                        component="div"
                        className="alert alert-danger"
                      />
                    </div>
                  </div>

                  <div className="field-container">
                    <label htmlFor="password" className="field-label">
                      <img src="/images/login/password.png" alt="password-img" />
                    </label>
                    <div className="field-input">
                      <Field name="password" type="password" placeholder={t("login.password")} className="form-field" />
                      <ErrorMessage
                        name="password"
                        component="div"
                        className="alert alert-danger"
                      />
                    </div>
                  </div>

                  <div className="field-container">
                    <label htmlFor="repeatPassword" className="field-label">
                      <img src="/images/login/password.png" alt="password-img" />
                    </label>
                    <div className="field-input">
                      <Field name="repeatPassword" type="password" placeholder={t("login.repeat_password")} className="form-field" />
                      <ErrorMessage
                        name="repeatPassword"
                        component="div"
                        className="alert alert-danger"
                      />
                    </div>
                  </div>

                  <div>
                    <label htmlFor="role user">
                      <Field type="radio" name="role" value="user" /> {t("login.role_user")}
                    </label>
                    <label htmlFor="role trainer">
                      <Field type="radio" name="role" value="trainer" /> {t("login.role_trainer")}
                    </label>
                    <label htmlFor="role admin">
                      <Field type="radio" name="role" value="admin" /> {t("login.role_admin")}
                    </label>
                  </div>

                  <div className="field-container">
                    <div className="field-input">
                      <label>
                        <Field type="checkbox" name="termsAccepted" />
                        <span onClick={this.showTermsModal} style={{ cursor: 'pointer', textDecoration: 'underline' }}>
                          {t("login.terms_accepted")}
                        </span>
                      </label>
                      <ErrorMessage
                        name="termsAccepted"
                        component="div"
                        className="alert alert-danger"
                      />
                    </div>
                  </div>

                  <div className="field-container">
                    <div className="field-input">
                      <label>
                        <Field type="checkbox" name="privacyAccepted" />
                        <span onClick={this.showPrivacyModal} style={{ cursor: 'pointer', textDecoration: 'underline' }}>
                          {t("login.privacy_accepted")}
                        </span>
                        <ErrorMessage
                          name="privacyAccepted"
                          component="div"
                          className="alert alert-danger"
                        />
                      </label>
                    </div>



                  </div>

                  <div className="field-container">
                    <ReCAPTCHA
                      sitekey={RECAPTCHA_SITE_KEY}
                      onChange={(token) => {
                        setFieldValue("recaptchaToken", token);
                        this.onRecaptchaChange(token);
                      }}
                    />
                    <ErrorMessage
                      name="recaptchaToken"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div >
                    <button type="submit">{t("login.register")}</button>
                  </div>
                </div>
              )}

              {message && (
                <div className="field-container">
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
          )}
        </Formik>

        <Modal show={showTermsModal} onClose={this.hideTermsModal} title={t("login.terms")}>
          <Terms />
        </Modal>

        <Modal show={showPrivacyModal} onClose={this.hidePrivacyModal} title={t("login.privacy")}>
          <Privacy />
        </Modal>
      </div>
    );
  }
}

export default withTranslation("global")(Register);

