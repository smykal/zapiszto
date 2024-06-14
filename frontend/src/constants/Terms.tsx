import React, { Component } from "react";
import { withTranslation } from "react-i18next";

type Props = {
    t: any;
};

class Terms extends Component<Props> {
    render() {
        const { t } = this.props;
        return (
            <div>
                <p>
                    <p>{t('terms.title')}</p>
                    <p>{t('terms.content')}</p>
                </p>
            </div>
        );
    }
}

export default withTranslation("terms")(Terms);

