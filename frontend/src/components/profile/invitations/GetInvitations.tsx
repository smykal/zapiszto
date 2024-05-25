import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';
import Service from '../../../services/invitations/';
import SingleInvitation from './SingleInvitation';
import ApproveInvitation from './ApproveInvitation';
import App from '../../../App';
import RejectInvitation from './RejectInvitation';

type Props = {
    t: any
};

type State = {
    invitations: Invitation[];
    invitationsSent: Invitation[];
    invitationsRecived: Invitation[];
    invitationsAccepted: Invitation[];
    invitationsRejected: Invitation[];
};

class GetInvitations extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            invitations: [],
            invitationsSent: [],
            invitationsRecived: [],
            invitationsAccepted: [],
            invitationsRejected: []
        };
    }

    componentDidMount(): void {
        this.getInvitations()
    }

    getInvitations() {
        Service.getInvitations()
            .then(response => {
                const invitations = response.data;
                this.setState({
                    invitations,
                    invitationsSent: invitations.filter((invitation: Invitation) => invitation.status === 'SENT'),
                    invitationsRecived: invitations.filter((invitation: Invitation) => invitation.status === 'RECIVED'),
                    invitationsAccepted: invitations.filter((invitation: Invitation) => invitation.status === 'APPROVED'),
                    invitationsRejected: invitations.filter((invitation: Invitation) => invitation.status === 'REJECTED')
                });
            })
            .catch(error => {
                console.error('Błąd podczas pobierania zaproszeń:', error);
            });
    }

    render() {
        const { t } = this.props;
        const { invitationsSent, invitationsRecived, invitationsAccepted, invitationsRejected } = this.state;

        return (
            <div>
                <h2>{t("invitations.title")}</h2>

                <h3>{t("invitations.title_sent")}</h3>
                <ul>
                    {invitationsSent.map(invitation => (
                        <li key={invitation.id}>
                            <SingleInvitation invitation={invitation} />
                        </li>
                    ))}
                </ul>


                <h3>{t("invitations.title_recived")}</h3>
                <ul>
                    {invitationsRecived.map(invitation => (
                        <li key={invitation.id}>
                            <SingleInvitation invitation={invitation} />
                            <ApproveInvitation invitation={invitation} />
                            <RejectInvitation invitation={invitation} />
                        </li>
                    ))}
                </ul>

                <h3>{t("invitations.title_approved")}</h3>
                <ul>
                    {invitationsAccepted.map(invitation => (
                        <li key={invitation.id}>
                            <SingleInvitation invitation={invitation} />
                        </li>
                    ))}
                </ul>

                <h3>{t("invitations.title_rejected")}</h3>
                <ul>
                    {invitationsRejected.map(invitation => (
                        <li key={invitation.id}>
                            <SingleInvitation invitation={invitation} />
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}

export default withTranslation("global")(GetInvitations);
