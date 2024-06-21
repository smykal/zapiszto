import React, { Component } from "react";
import Service from "../../services/bodyParams";
import Collapsible from 'react-collapsible';
import ShowDiagram from "./showDiagramOne";
import ShowBmi from "./showBmi";
import ShowBmr from "./showBmr"
import ShowCpf from "./showCpf"
import Gender from "./gender"
import Age from "./age"
import { BodyParamsItem } from '../../types/types';
import { withTranslation } from "react-i18next";



type Props = {
    t: any;
};
type State = {
    bodyParams: BodyParamsItem[];
    expandedItemIndex: number | null;
    bmiExpanded: boolean;
    genderExpanded: boolean;
    ageExpanded: boolean;
    bmrExpanded: boolean;
    cpfExpanded: boolean;
};

class ShowBodyParams extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bodyParams: [],
            expandedItemIndex: null,
            bmiExpanded: false,
            genderExpanded: false,
            ageExpanded: false,
            bmrExpanded: false,
            cpfExpanded: false,
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

    handleGenderClick() {
        this.setState((prevState) => ({
            genderExpanded: !prevState.genderExpanded, // Odwrócenie stanu dla ShowBmi
        }));
    }

    handleAgeClick() {
        this.setState((prevState) => ({
            ageExpanded: !prevState.ageExpanded, // Odwrócenie stanu dla ShowAge
        }));
    }

    handleBmrClick() {
        this.setState((prevState) => ({
            bmrExpanded: !prevState.bmrExpanded, // Odwrócenie stanu dla ShowBmr
        }));
    }

    handleCpfClick() {
        this.setState((prevState) => ({
            cpfExpanded: !prevState.cpfExpanded,
        }));
    }

    isItemExpanded(index: number): boolean {
        return this.state.expandedItemIndex === index;
    }

    render() {
        const { bmrExpanded, bmiExpanded, genderExpanded, ageExpanded, cpfExpanded } = this.state;
        const { t } = this.props;

        return (
            <div>
                <ul>
                    <li>
                        <Collapsible
                            trigger={
                                <div
                                    style={{
                                        cursor: 'pointer',
                                        display: 'flex',
                                        flexDirection: 'column',
                                        marginBottom: '10px',
                                    }}
                                    onClick={() => this.handleGenderClick()}
                                >
                                    <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                        {t("body_params.gender")}
                                    </span>
                                </div>
                            }
                            open={genderExpanded} // Ustawienie czy jest rozwinięty czy nie
                        >
                            <Gender />
                        </Collapsible>
                    </li>
                    <li>
                        <Collapsible
                            trigger={
                                <div
                                    style={{
                                        cursor: 'pointer',
                                        display: 'flex',
                                        flexDirection: 'column',
                                        marginBottom: '10px',
                                    }}
                                    onClick={() => this.handleAgeClick()}
                                >
                                    <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                        {t("body_params.age")}
                                    </span>
                                </div>
                            }
                            open={ageExpanded} // Ustawienie czy jest rozwinięty czy nie
                        >
                            <Age />
                        </Collapsible>
                    </li>
                    <li>
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
                                        {t("body_params.body_mass_index")}
                                    </span>
                                </div>
                            }
                            open={bmiExpanded} // Ustawienie czy jest rozwinięty czy nie
                        >
                            <ShowBmi />
                        </Collapsible>
                    </li>
                    <li>
                        <Collapsible
                            trigger={
                                <div
                                    style={{
                                        cursor: 'pointer',
                                        display: 'flex',
                                        flexDirection: 'column',
                                        marginBottom: '10px',
                                    }}
                                    onClick={() => this.handleBmrClick()}
                                >
                                    <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                        {t("body_params.basal_metabolic_rate")}
                                    </span>
                                </div>
                            }
                            open={bmrExpanded} // Ustawienie czy jest rozwinięty czy nie
                        >
                            <ShowBmr />
                        </Collapsible>
                    </li>
                    <li>
                        <Collapsible
                            trigger={
                                <div
                                    style={{
                                        cursor: 'pointer',
                                        display: 'flex',
                                        flexDirection: 'column',
                                        marginBottom: '10px',
                                    }}
                                    onClick={() => this.handleCpfClick()}
                                >
                                    <span style={{ marginRight: '10px', width: '150px', textAlign: 'right' }}>
                                        {t("body_params.carbs_fat_protein")}
                                    </span>
                                </div>
                            }
                            open={cpfExpanded} // Ustawienie czy jest rozwinięty czy nie
                        >
                            <ShowCpf />
                        </Collapsible>
                    </li>
                    {this.state.bodyParams.length > 0 && (
                        this.state.bodyParams.map((item: BodyParamsItem, index: number) => (
                            <li key={index}>
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
                        ))
                    )}
                </ul>
            </div>
        );

    }
}

export default withTranslation("global")(ShowBodyParams)