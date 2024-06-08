import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';
import Service from '../../../services/invitations/';
import SingleInvitation from './SingleInvitation';
import ApproveInvitation from './ApproveInvitation';
import RejectInvitation from './RejectInvitation';
import Collapsible from 'react-collapsible';
import SendInvitation from "../invitations/SendInvitation";


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
        this.getInvitations();
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
            <div className="container">
                <header className="jumbotron">
                    <h3>{t("invitations.title")}</h3>
                    <SendInvitation />

                    <Collapsible trigger={`${t("invitations.title_sent")} (${invitationsSent.length})`} open={false}>
                        <ul>
                            {invitationsSent.length > 0 ? (
                                invitationsSent.map(invitation => (
                                    <li key={invitation.id} >
                                        <SingleInvitation invitation={invitation} />
                                    </li>
                                ))
                            ) : (
                                <li>{t("invitations.no_sent")}</li>
                            )}
                        </ul>
                    </Collapsible>

                    <Collapsible trigger={`${t("invitations.title_recived")} (${invitationsRecived.length})`} open={false}>
                        <ul>
                            {invitationsRecived.length > 0 ? (
                                invitationsRecived.map(invitation => (
                                    <li key={invitation.id}>
                                        <SingleInvitation invitation={invitation} />
                                        <ApproveInvitation invitation={invitation} />
                                        <RejectInvitation invitation={invitation} />
                                    </li>
                                ))
                            ) : (
                                <li>{t("invitations.no_recived")}</li>
                            )}
                        </ul>
                    </Collapsible>

                    <Collapsible trigger={`${t("invitations.title_approved")} (${invitationsAccepted.length})`} open={false}>
                        <ul>
                            {invitationsAccepted.length > 0 ? (
                                invitationsAccepted.map(invitation => (
                                    <li key={invitation.id}>
                                        <SingleInvitation invitation={invitation} />
                                    </li>
                                ))
                            ) : (
                                <li>{t("invitations.no_approved")}</li>
                            )}
                        </ul>
                    </Collapsible>

                    <Collapsible trigger={`${t("invitations.title_rejected")} (${invitationsRejected.length})`} open={false}>
                        <ul>
                            {invitationsRejected.length > 0 ? (
                                invitationsRejected.map(invitation => (
                                    <li key={invitation.id}>
                                        <SingleInvitation invitation={invitation} />
                                    </li>
                                ))
                            ) : (
                                <li>{t("invitations.no_rejected")}</li>
                            )}
                        </ul>
                    </Collapsible>
                </header>
            </div>
        );
    }
}

export default withTranslation("global")(GetInvitations);
