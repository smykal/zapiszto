import React, { Component } from "react";
import { withTranslation } from "react-i18next";

type Props = {
  t: any;
};

class Privacy extends Component<Props> {
  render() {
    const { t } = this.props;
    return (
      <div>
        <p>{t('privacy.content')}</p>
      </div>
    );
  }
}

export default withTranslation("privacy")(Privacy);