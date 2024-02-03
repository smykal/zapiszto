import { Component } from "react";
import Service from "../../services/zapiszto"


type BodyParamsItem = {
    dict_body_params_name: string;
    value: string;
    userId: number;
};

type Props = {};
type State = {
    bodyParams: BodyParamsItem[];
};

export default class ShowBodyParams extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bodyParams: []
        };
    }

    componentDidMount() {
        Service.getActualBodyParams().then(
            (response) => {
                this.setState({
                    bodyParams: response.data,
                });
            },
            (error) => {
                console.log("jakis błąd", error)
            }
        )
    }
    render() {
        return (
            <div className="container">
                <header className="jumbotron">
                    <ul>
                        {this.state.bodyParams.map((item: BodyParamsItem, index: number) => (
                            <li key={index} id="bodyParams">
                                <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>{item.dict_body_params_name}:</span>
                                <span style={{ marginRight: '10px' }}>{item.value},</span>

                                <strong>userId:</strong> {item.userId}
                            </li>
                        ))}
                    </ul>
                </header>
            </div>
        );
    }
}