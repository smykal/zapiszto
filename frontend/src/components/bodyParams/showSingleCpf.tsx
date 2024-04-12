import React, { Component } from "react";
import { CpfType } from "../../types/types";

type Props = {
  param_name: CpfType;
  cpfType: CpfType[];
};

type State = {};

class ShowSingleCpf extends Component<Props, State> {
  render() {
    const { param_name, cpfType } = this.props;
    return (
      <div>
            <p>bilans kalorii: {param_name.kcalMin} -- {param_name.kcalMax} kcal</p>
            <p>węglowodany: {param_name.carbohydrateMin} --{param_name.carbohydrateMax} g</p>
            <p>białko: {param_name.proteinMin} -- {param_name.proteinMax} g</p>
            <p>tłuszcz: {param_name.fatMin} -- {param_name.fatMax} g</p>
        {/* Tutaj możesz wyświetlić inne informacje z param_name i cpfType */}
      </div>
    );
  }
}

export default ShowSingleCpf;
