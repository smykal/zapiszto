import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
import { NewInvitation } from '../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import Service from '../../../services/invitations/';

const SendInvitation: React.FC = () => {
    const { t } = useTranslation('global');
    const [invitationSent, setInvitationSent] = useState(false);

    const postInvitation = (values: { email: string }) => {
        const newInvitation: NewInvitation = {
            email: values.email
        };
        Service.postNewInvitation(newInvitation)
            .then(() => {
                setInvitationSent(true);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zaproszenia:', error);
            });
    };

    return (
        <div>
            <Formik
                initialValues={{ email: '' }}
                validationSchema={Yup.object({
                    email: Yup.string()
                        .email(t("validation.invalid_email"))
                        .required(t("validation.this_field_is_required"))
                })}
                onSubmit={(values, { setSubmitting, resetForm }) => {
                    setTimeout(() => {
                        postInvitation(values);
                        setSubmitting(false);
                        resetForm(); // Czyszczenie formularza po wysłaniu
                    }, 400);
                }}
            >
                {formik => (
                    <Form>
                        <Field name="email" type="email" placeholder={t("buttons.placeholder_send_invitation")} />
                        <ErrorMessage name="email" component="div" />
                        <button type="submit" disabled={formik.isSubmitting}>{t("buttons.send_invitation")}</button>
                        {invitationSent && <div>{t('clients.invitation_sent')}</div>}
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default SendInvitation;
