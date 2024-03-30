import React, { Component } from "react";
import Service from "../../services/bodyParams";
import Collapsible from 'react-collapsible';
import ShowDiagram from "./showDiagramOne";
import ShowBmi from "./showBmi";
import { BodyParamsItem } from '../../types/types';


type Props = {};
type State = {
    bodyParams: BodyParamsItem[];
    expandedItemIndex: number | null;
    bmiExpanded: boolean;
};

export default class ShowBodyParams extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bodyParams: [],
            expandedItemIndex: null,
            bmiExpanded: false, // Dodanie stanu dla ShowBmi
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

    handleBmiClick() {
        this.setState((prevState) => ({
            bmiExpanded: !prevState.bmiExpanded, // Odwrócenie stanu dla ShowBmi
        }));
    }

    isItemExpanded(index: number): boolean {
        return this.state.expandedItemIndex === index;
    }

    render() {

        const { bmiExpanded } = this.state;
        return (
            <div className="container">
                <header className="jumbotron">
                    <ul>
                        {this.state.bodyParams.map((item: BodyParamsItem, index: number) => (
                            <li key={index} id="bodyParams">
                                <Collapsible
                                    trigger={
                                        <div
                                            style={{
                                                cursor: 'pointer',
                                                display: 'flex',
                                                flexDirection: 'column',
                                                marginBottom: '10px',
                                            }}
                                            onClick={() => this.handleItemClick(index)}
                                        >
                                            <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                                {item.dict_body_params_name}: {item.value}
                                            </span>
                                        </div>
                                    }
                                >
                                    <ShowDiagram param_name={item.dict_body_params_name}></ShowDiagram>
                                </Collapsible>
                            </li>
                        ))}
                        <li id="bodyParams">
                            <Collapsible
                                trigger={
                                    <div
                                        style={{
                                            cursor: 'pointer',
                                            display: 'flex',
                                            flexDirection: 'column',
                                            marginBottom: '10px',
                                        }}
                                        onClick={() => this.handleBmiClick()}
                                    >
                                        <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                            BMI
                                        </span>
                                    </div>
                                }
                                open={bmiExpanded} // Ustawienie czy jest rozwinięty czy nie
                            >
                                <ShowBmi />
                            </Collapsible>
                        </li>
                    </ul>
                </header>
                <ShowBmi />
            </div>
        );
    }
}