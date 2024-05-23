import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import Service from '../../../services/invitations/';

type Props = {
    t: any
};

type State = {
    invitations: Invitation[]
};

class GetInvitations extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            invitations: []
        };
    }

    componentDidMount(): void {
        this.getInvitations()
    }

    getInvitations() {
        Service.getInvitations()
        .then(response => {
            this.setState({ invitations: response.data })
        })
        .catch(error => {
            console.error('Błąd podczas pobierania zaproszeń:', error);
        });
    }

    render() {
        const { t } = this.props;
        const { invitations } = this.state;

        return (
            <div>
                <h2>{t("invitations.title")}</h2>
                <ul>
                    {invitations.map(invitation => (
                        <li key={invitation.id}>
                            <div>
                                <strong>{t("invitations.from")}: </strong>{invitation.inviterName} ({invitation.inviterEmail})
                            </div>
                            <div>
                                <strong>{t("invitations.to")}: </strong>{invitation.inviteeName} ({invitation.inviteeEmail})
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}

export default withTranslation("global")(GetInvitations);
