import React, { Component } from "react";
import Service from "../../services/bodyParams";
import { CpfType } from "../../types/types"; 
import Collapsible from "react-collapsible";
import ShowSingleCpf from "./showSingleCpf";

type Props = {};
type State = {
    cpfReduction: CpfType[]; 
    cpfRegular: CpfType[];
    cpfMass: CpfType[];
    expandedItemIndex: number | null;
}

export default class GetCpf extends Component<Props, State> {
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
            if (cpfList.length >= 3) { // Upewnij się, że dostajesz trzy listy danych
              const cpfReduction = cpfList[0];
              const cpfRegular = cpfList[1];
              const cpfMass = cpfList[2];
              this.setState({ cpfReduction, cpfRegular, cpfMass });
            } else {
              console.log('Błąd: Nieprawidłowy format danych CPF');
            }
          },
          (error) => {
            console.log("Błąd:", error);
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
        return (
            <div className="container" style={{ display: 'flex' }}>
                <div className="left" style={{ flex: '1', marginRight: '10px' }}>
                    <p>redukcja</p>
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
                    <p>regular</p>
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
                    <p> mass</p>
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
