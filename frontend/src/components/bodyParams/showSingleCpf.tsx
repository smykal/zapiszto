import React, { Component } from "react";
import { CpfType } from "../../types/types";
import { withTranslation } from "react-i18next";


type Props = {
  param_name: CpfType;
  cpfType: CpfType[];
  t: any;
};

type State = {};

class ShowSingleCpf extends Component<Props, State> {
  render() {
    const { param_name, cpfType } = this.props;
    const { t } = this.props;

    return (
      <div>
            <p>{t("body_params.kcal")}: {param_name.kcalMin} / {param_name.kcalMax} kcal</p>
            <p>{t("body_params.carbs")}: {param_name.carbohydrateMin} / {param_name.carbohydrateMax} g</p>
            <p>{t("body_params.protein")}: {param_name.proteinMin} / {param_name.proteinMax} g</p>
            <p>{t("body_params.fat")}: {param_name.fatMin} / {param_name.fatMax} g</p>
        {/* Tutaj możesz wyświetlić inne informacje z param_name i cpfType */}
      </div>
    );
  }
}

export default withTranslation("global")(ShowSingleCpf)
