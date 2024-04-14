import React, { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Service from '../../services/workbooks';
import 'react-tabs/style/react-tabs.css';
import { Workbook } from '../../types/types';


type Props = {};
type State = {
    workbooks: Workbook[];
    newWorkbookName: string; 
};

export default class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            workbooks: [],
            newWorkbookName: ''
        };
    }

    componentDidMount() {
        this.getWorkbooks();
    }

    getWorkbooks() {
        Service.getWorkbooks()
            .then(response => {
                const workbooks: Workbook[] = response.data;
                this.setState({ workbooks });
            })
            .catch(error => console.error('Error fetching workbooks:', error));
    }

    handleNewWorkbookNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({ newWorkbookName: event.target.value }); // Aktualizujemy stan nowej nazwy workbooka
    };

    handleAddWorkbook = () => {
        const { newWorkbookName } = this.state;
        if (newWorkbookName.trim() !== '') { // Sprawdzamy, czy nazwa jest niepusta
            Service.postNewWorkbook(newWorkbookName)
                .then(() => {
                    // Pobieramy zaktualizowaną listę workbooków po dodaniu nowego
                    this.getWorkbooks();
                    // Czyścimy pole na nową nazwę workbooka po dodaniu
                    this.setState({ newWorkbookName: '' });
                });
        }
    };

    render() {
        const { workbooks, newWorkbookName } = this.state;

        return (
            <div className="container">
                <div className="input_workbook">
                    <input
                        type="text"
                        value={newWorkbookName}
                        onChange={this.handleNewWorkbookNameChange} // Obsługa zmiany w polu tekstowym
                        placeholder="Enter new workbook name"
                    />
                    <button onClick={this.handleAddWorkbook}>Add Workbook</button>
                </div>
                <div>
                    <Tabs>
                        <TabList>
                            {workbooks.map((workbook, index) => (
                                <Tab key={index}>{workbook.name}</Tab>
                            ))}
                        </TabList>
                        {workbooks.map((workbook, index) => (
                            <TabPanel key={index}>
                                <h2>{workbook.name}</h2>
                                <p>Id number: {workbook.id}</p>
                                <p>Order number: {workbook.order_number}</p>
                                <p>Insert date: {workbook.insert_date}</p>
                                <p>actual index {index + 1}</p>
                            </TabPanel>
                        ))}
                    </Tabs>
                </div>
            </div>
        );
    }
}
