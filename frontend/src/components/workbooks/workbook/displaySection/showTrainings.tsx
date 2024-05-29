import { Component } from "react";
import { Training } from "../../../../types/types";
import Service from "../../../../services/workbooks";
import Collapsible from "react-collapsible";
import Exercises from "./exercises/index";

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

        // Odczytujemy indeks rozwiniętego elementu z localStorage i ustawiamy go w stanie komponentu
        const expandedItemIndex = localStorage.getItem('expandedItemIndex');
        if (expandedItemIndex !== null) {
            this.setState({ expandedItemIndex: parseInt(expandedItemIndex) });
        }
    }

    handleItemClick(index: number) {
        this.setState((prevState) => ({
            expandedItemIndex: prevState.expandedItemIndex === index ? null : index
        }));

        // Zapisujemy indeks rozwiniętego elementu do localStorage
        localStorage.setItem('expandedItemIndex', index.toString());
    }

    isItemExpanded(index: number): boolean {
        return this.state.expandedItemIndex === index;
    }

    render() {
        return (
            <div>
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
                                        <span style={{ marginRight: '10px', width: '350px', textAlign: 'right' }}>
                                            {item.date}: {item.notes},workbook_id :{item.workbooks_id}, training_id: {item.id}
                                        </span>
                                    </div>
                                }
                                open={this.isItemExpanded(index)}
                            >
                                {/* Treść, którą chcesz wyświetlić po rozwinięciu */}
                                <Exercises workbook_id={item.workbooks_id} training_id={item.id} />
                            </Collapsible>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}
