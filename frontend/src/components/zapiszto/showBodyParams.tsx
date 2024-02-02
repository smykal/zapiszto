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
        const listItemStyle = {
            marginBottom: "10px",
            padding: "10px",
            border: "1px solid #ccc",
            backgroundColor: "#f9f9f9",
          };

        return (
            <div className="container">
                <header className="jumbotron">
                    <ul>
                        {this.state.bodyParams.map((item: BodyParamsItem, index: number) => (
                            <li key={index} style={listItemStyle}>
                                <strong>Kolumna 1:</strong> {item.dict_body_params_name},{" "}
                                <strong>Kolumna 2:</strong> {item.value},{" "}
                                <strong>userId:</strong> {item.userId}
                            </li>
                        ))}
                    </ul>
                </header>
            </div>

        );
    }

}