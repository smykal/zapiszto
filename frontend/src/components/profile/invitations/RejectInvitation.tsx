import React, { Component } from 'react';
import { withTranslation } from "react-i18next";
import { Invitation } from '../../../types/types';
import Service from '../../../services/invitations'

type Props = {
    invitation: Invitation;
    t: any;
};

class RejectInvitation extends Component<Props> {
    handleRejectClick = () => {
        const { invitation } = this.props;
        Service.postRejectInvitation(invitation)
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
                <button onClick={this.handleRejectClick}>{t('buttons.reject')}</button>
            </div>
        );
    }
}

export default withTranslation("global")(RejectInvitation);
