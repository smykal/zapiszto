import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { NewInvitation } from '../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import Service from '../../../services/invitations/';

type Props = {
    t: any
};

type State = {
    invitationSent: boolean;
};

class SendInvitation extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            invitationSent: false
        };
    }

    postInvitation(values: { email: string }) {
        const newInvitation: NewInvitation = {
            email: values.email
        };
        Service.postNewInvitation(newInvitation)
            .then(() => {
                this.setState({ invitationSent: true });
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zaproszenia:', error);
            });
    }

    render() {
        const { t } = this.props;
        const { invitationSent } = this.state;

        return (
            <div>
                <Formik
                    initialValues={{ email: '' }}
                    validationSchema={Yup.object({
                        email: Yup.string()
                            .email("Niepoprawny email")
                            .required(t("validation.this_field_is_required"))
                    })}
                    onSubmit={(values, { setSubmitting, resetForm }) => {
                        setTimeout(() => {
                            this.postInvitation(values);
                            setSubmitting(false);
                            resetForm(); // Czyszczenie formularza po wysłaniu
                        }, 400);
                    }}
                >
                    {formik => (
                        <Form>
                            <Field name="email" type="email" />
                            <ErrorMessage name="email" component="div" />
                            <button className='myButton' type="submit" disabled={formik.isSubmitting}>{t("buttons.add")}</button>
                            {invitationSent && <div>Zaproszenie zostało wysłane.</div>}
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}

export default withTranslation("global")(SendInvitation);
