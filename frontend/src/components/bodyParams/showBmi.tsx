import React, {Component} from "react";
import Service from "../../services/bodyParams";
import { BmiItem } from "../../types/types";

type Props = {};
type State = {
    bmi: BmiItem | null;
}

export default class ShowBmi extends Component<Props,State> {
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
                    // Konwersja formatu daty
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
        return (
            <div className="container">
                {bmi &&
                    <div>
                        <p>
                            <strong>Data:</strong> {bmi.date}
                            <strong> Wartość BMI:</strong> {bmi.value}
                            <strong> Opis:</strong> {bmi.description}
                        </p>
                    </div>
                }
            </div>
        )
    }
}