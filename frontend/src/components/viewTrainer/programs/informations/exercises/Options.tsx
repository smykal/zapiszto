import React, { Component } from "react";
import Service from '../../../../../services/exercises';
import { withTranslation } from "react-i18next";

type Props = {
  exerciseId: string;
  sessionId: string;
  t: any;
};
type State = {};

class Options extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
  }

  render() {
    const { exerciseId, sessionId, t } = this.props;
    return (
      <div>
        <button>{t("buttons.delete")}</button>
        <button>{t("buttons.edit")} </button>
        <button>{t("buttons.move_up")}</button>
        <button>{t("buttons.move_down")}</button>
        <button>{t("buttons.archive")} </button>
      </div>
    );
  }
}

export default withTranslation("global")(Options);
