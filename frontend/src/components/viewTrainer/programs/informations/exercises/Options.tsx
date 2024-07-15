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
        <button>d</button>
        <button>/</button>
        <button>\</button>
      </div>
    );
  }
}

export default withTranslation("global")(Options);
