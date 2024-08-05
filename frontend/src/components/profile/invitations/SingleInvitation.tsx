import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';

type Props = {
    invitation: Invitation;
    t: any;
};

class SingleInvitation extends Component<Props> {
    render() {
        const { invitation, t } = this.props;
        return (
            <div className="single-invitation">
                <div>
                    id: {invitation.id}
                </div>
                <div>
                    <strong>{t("invitations.from")}: </strong>{invitation.inviterName} ({invitation.inviterEmail})
                    <br />
                    <strong>{t("invitations.to")}: </strong>{invitation.inviteeName} ({invitation.inviteeEmail})
                </div>
                <div className="invitation-actions">
                    {/* Dodaj odpowiednie przyciski akcji */}
                </div>
            </div>
        );
    }
}

export default withTranslation("global")(SingleInvitation);