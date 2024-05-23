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
            <div>
                <div>
                    id: {invitation.id}
                </div>
                <div>
                    <strong>{t("invitations.from")}: </strong>{invitation.inviterName} ({invitation.inviterEmail})
                
                    <strong>{t("invitations.to")}: </strong>{invitation.inviteeName} ({invitation.inviteeEmail})
                </div>
                WHEN status is ?? then select from :
                Usuń Zaproszenie | Zaakceptuj | odrzuć | wyślij ponownie
                TERMINATE | RESENT | APPROVE | REJECT  
            </div>
        );
    }
}

export default withTranslation("global")(SingleInvitation);
