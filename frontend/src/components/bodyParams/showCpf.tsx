import React, { Component } from "react";
import Service from "../../services/bodyParams";
import { CpfType } from "../../types/types"; 
import Collapsible from "react-collapsible";
import ShowSingleCpf from "./showSingleCpf";
import { withTranslation } from "react-i18next";


type Props = {
    t: any;
};
type State = {
    cpfReduction: CpfType[]; 
    cpfRegular: CpfType[];
    cpfMass: CpfType[];
    expandedItemIndex: number | null;
}
class GetCpf extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            cpfReduction: [],
            cpfRegular: [],
            cpfMass: [],
            expandedItemIndex: null,
        };
    }

    componentDidMount() {
        Service.getCpf().then(
            (response) => {
                const cpfList = response.data;
                if (cpfList.length >= 3) {
                    const cpfReduction = cpfList[0];
                    const cpfRegular = cpfList[1];
                    const cpfMass = cpfList[2];
                    this.setState({ cpfReduction, cpfRegular, cpfMass });
                } else {
                    console.log('Error: Invalid CPF data format');
                }
            },
            (error) => {
                console.log("Error:", error);
            }
        );
    }

    handleItemClick(index: number) {
        this.setState((prevState) => ({
            expandedItemIndex: prevState.expandedItemIndex === index ? null : index,
        }));
    }

    render() {
        const { cpfReduction, cpfRegular, cpfMass } = this.state;
        const { t } = this.props;

        return (
            <div className="container" style={{ display: 'flex' }}>
                <div className="left" style={{ flex: '1', marginRight: '10px' }}>
                    <p>{t("body_params.cpf_reduction")}</p>
                    {cpfReduction.map((item: CpfType, index: number) => (
                        <li key={index} id="bodyParams">
                            <Collapsible
                                trigger={
                                    <div
                                        style={{
                                            color: 'red',
                                            cursor: 'pointer',
                                            display: 'flex',
                                            flexDirection: 'column',
                                            marginBottom: '10px',
                                        }}
                                        onClick={() => this.handleItemClick(index)}
                                    >
                                        <span style={{ marginRight: '10px', width: '100%', textAlign: 'center' }}>
                                            {item.activity_level}
                                        </span>
                                    </div>
                                }
                            >
                                <ShowSingleCpf param_name={item} cpfType={cpfReduction}></ShowSingleCpf>
                            </Collapsible>
                        </li>
                    ))}
                </div>
                <div className="middle" style={{ flex: '1', marginRight: '10px' }}>
                    <p>{t("body_params.cpf_regular")}</p>
                    {cpfRegular.map((item: CpfType, index: number) => (
                        <li key={index} id="bodyParams">
                            <Collapsible
                                trigger={
                                    <div
                                        style={{
                                            color: 'red',
                                            cursor: 'pointer',
                                            display: 'flex',
                                            flexDirection: 'column',
                                            marginBottom: '10px',
                                        }}
                                        onClick={() => this.handleItemClick(index)}
                                    >
                                        <span style={{ marginRight: '10px', width: '100%', textAlign: 'center' }}>
                                            {item.activity_level}
                                        </span>
                                    </div>
                                }
                            >
                                <ShowSingleCpf param_name={item} cpfType={cpfRegular}></ShowSingleCpf>
                            </Collapsible>
                        </li>
                    ))}
                </div>
                <div className="right" style={{ flex: '1' }}>
                    <p>{t("body_params.cpf_mass")}</p>
                    {cpfMass.map((item: CpfType, index: number) => (
                        <li key={index} id="bodyParams">
                            <Collapsible
                                trigger={
                                    <div
                                        style={{
                                            color: 'red',
                                            cursor: 'pointer',
                                            display: 'flex',
                                            flexDirection: 'column',
                                            marginBottom: '10px',
                                        }}
                                        onClick={() => this.handleItemClick(index)}
                                    >
                                        <span style={{ marginRight: '10px', width: '100%', textAlign: 'center' }}>
                                            {item.activity_level}
                                        </span>
                                    </div>
                                }
                            >
                                <ShowSingleCpf param_name={item} cpfType={cpfMass}></ShowSingleCpf>
                            </Collapsible>
                        </li>
                    ))}
                </div>
            </div>
        );
    }
}

export default withTranslation("global")(GetCpf);
