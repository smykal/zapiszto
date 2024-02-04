import React, { Component } from "react";
import Service from "../../services/zapiszto";
import ShowShow from "./ShowShow";

type BodyParamsItem = {
    dict_body_params_name: string;
    value: string;
    userId: number;
};

type Props = {};
type State = {
    bodyParams: BodyParamsItem[];
    expandedItemIndex: number | null;
};

export default class ShowBodyParams extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bodyParams: [],
            expandedItemIndex: null,
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
                console.log("jakis błąd", error);
            }
        );
    }

    handleItemClick(index: number) {
        this.setState((prevState) => ({
            expandedItemIndex: prevState.expandedItemIndex === index ? null : index,
        }));
    }

    isItemExpanded(index: number): boolean {
        return this.state.expandedItemIndex === index;
    }

    render() {
        return (
            <div className="container">
                <header className="jumbotron">
                    <ul>
                        {this.state.bodyParams.map((item: BodyParamsItem, index: number) => (
                            <li key={index} id="bodyParams">
                                <div
                                    style={{ cursor: 'pointer' }}
                                    onClick={() => this.handleItemClick(index)}
                                >
                                    <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                        {item.dict_body_params_name}:
                                    </span>
                                    <span style={{ marginRight: '10px' }}>{item.value}</span>
                                </div>
                                <div>
                                  {this.isItemExpanded(index) && (
                                    <ShowShow parameter={item.dict_body_params_name} />
                                )}  
                                </div>
                                
                            </li>
                        ))}
                    </ul>
                </header>
            </div>
        );
    }
}
