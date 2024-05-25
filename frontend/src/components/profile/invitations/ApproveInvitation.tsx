import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';
import Service from '../../../services/invitations'

type Props = {
    invitation: Invitation;
    t: any;
};

class ApproveInvitation extends Component<Props> {
    handleApproveClick = () => {
        const { invitation } = this.props;
        Service.postApproveInvitation(invitation)
            .then(() => {
                // Dodaj obsługę odpowiedzi po pomyślnym wysłaniu zaproszenia
            })
            .catch(error => {
                // Dodaj obsługę błędu
                console.error('Błąd podczas wysyłania zaproszenia:', error);
            });
    };

    render() {
        const { t } = this.props;
        return (
            <div>
                <button onClick={this.handleApproveClick}>{t('buttons.approve')}</button>
            </div>
        );
    }
}

export default withTranslation("global")(ApproveInvitation);
