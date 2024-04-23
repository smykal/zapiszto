import React, { Component } from "react";
import { Training } from "../../../../types/types";
import Service from "../../../../services/workbooks";
import Collapsible from "react-collapsible";

type Props = {
    workbook_id: number;
};

type State = {
    trainings: Training[];
    expandedItemIndex: number | null;
};

export default class ShowTrainings extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            trainings: [],
            expandedItemIndex: null
        };
    }

    componentDidMount() {
        Service.getTrainings(this.props.workbook_id).then(
            (response) => {
                if (response.data.length > 0) {
                    const parsedTrainings = response.data.map((training: Training) => {
                        // Parsowanie daty
                        const parsedDate = new Date(training.date).toISOString().split('T')[0];
                        return {
                            ...training,
                            date: parsedDate
                        };
                    });
                    this.setState({
                        trainings: parsedTrainings
                    });
                }
            }
        );
    }

    handleItemClick(index: number) {
        this.setState((prevState) => ({
            expandedItemIndex: prevState.expandedItemIndex === index ? null : index
        }));
    }

    isItemExpanded(index: number): boolean {
        return this.state.expandedItemIndex === index;
    }

    render() {
        return (
            <div className="container">
                    <ul>
                        {this.state.trainings.map((item: Training, index: number) => (
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
                                                {item.date}: {item.notes}
                                            </span>
                                        </div>
                                    }
                                    open={this.isItemExpanded(index)}
                                >
                                    <p>data: {item.date}</p>
                                    <p>id: {item.id}</p>
                                    <p>workbook id: {item.workbooks_id}</p>
                                    <p>notes: {item.notes}</p>
                                    {/* Treść, którą chcesz wyświetlić po rozwinięciu */}
                                </Collapsible>
                            </li>
                        ))}
                    </ul>
            </div>
        );
    }
}
