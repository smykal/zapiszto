import React, { Component } from "react";
import Service from '../../../services/exercises';
import { withTranslation } from "react-i18next";

type Props = {
  item: number;
  onDeleteCategory: (id: number) => void;
  t: any;
};
type State = {};

class Options extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
  }

  delete = (item: number) => {
    Service.deleteDictCategory(item)
      .then(() => {
        this.props.onDeleteCategory(item);
      })
      .catch(error => {
        console.error('Error deleting category:', error);
      });
  }

  render() {
    const { item, t } = this.props;
    return (
      <div>
        <button onClick={() => this.delete(item)}>{t("buttons.delete")}</button>
        <button>{t("buttons.edit")}</button>
        <button>{t("buttons.archive")}</button>
      </div>
    );
  }
}

export default withTranslation("global")(Options);
