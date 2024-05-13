import React, { Component } from "react";
import { withTranslation } from "react-i18next";

import Service from "../../services/bodyParams";
import { BmiItem } from "../../types/types";

type Props = {
    t: any;
};
type State = {
    bmi: BmiItem | null;
}

class ShowBmi extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bmi: null
        };
    }

    componentDidMount() {
        Service.getBmiParams().then(
            (response) => {
                if (response.data.length > 0) {
                    const bmiData = response.data[0];
                    bmiData.date = new Date(bmiData.date).toISOString().split('T')[0];
                    this.setState({
                        bmi: bmiData
                    });
                }
            },
            (error) => {
                console.log("Błąd:", error);
            }
        )
    }

    render()  {
        const { bmi } = this.state;
        const { t } = this.props;

        return (
            <div className="container">
                {bmi &&
                    <div>
                        <p>
                            <strong>{t("table.date")}:</strong> {bmi.date}
                            <strong>{t("table.bmi_value")}:</strong> {bmi.value}
                            <strong>{t("table.description")}:</strong> {bmi.description}
                        </p>
                    </div>
                }
            </div>
        )
    }
}

export default withTranslation("global")(ShowBmi);
