import React, { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Service from '../../services/workbooks';
import 'react-tabs/style/react-tabs.css';
import { Workbook } from '../../types/types';
import Wrapper from './workbook';


type Props = {};
type State = {
    workbooks: Workbook[];
    newWorkbookName: string;
    originalOrder: Workbook[]; 
};

export default class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            workbooks: [],
            newWorkbookName: '',
            originalOrder: [], 
        };
    }

    componentDidMount() {
        this.getWorkbooks();
    }

    getWorkbooks() {
        Service.getWorkbooks()
            .then(response => {
                const workbooks: Workbook[] = response.data;
                const sortedWorkbooks = [...workbooks].sort((a, b) => a.order_number - b.order_number); // Sortujemy workbooki
                this.setState({ workbooks: sortedWorkbooks, originalOrder: [...sortedWorkbooks] });
            })
            .catch(error => console.error('Error fetching workbooks:', error));
    }

    handleNewWorkbookNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({ newWorkbookName: event.target.value });
    };

    handleAddWorkbook = () => {
        const { newWorkbookName } = this.state;
        if (newWorkbookName.trim() !== '') {
            Service.postNewWorkbook(newWorkbookName)
                .then(() => {
                    this.setState({ newWorkbookName: '' });
                })
                .then(() => {
                    this.getWorkbooks(); 
                });
        }
    };

    render() {
        const { workbooks, newWorkbookName, originalOrder } = this.state;

        return (
            <div className="container">
                <div className="input_workbook">
                    <input
                        type="text"
                        value={newWorkbookName}
                        onChange={this.handleNewWorkbookNameChange}
                        placeholder="Enter new workbook name"
                    />
                    <button onClick={this.handleAddWorkbook}>Add Workbook</button>
                </div>
                <div>
                    <Tabs>
                        <TabList>
                            {originalOrder.map((workbook) => (
                                <Tab key={workbook.id}>{workbook.name}</Tab>
                            ))}
                        </TabList>
                        {workbooks.map((workbook) => (
                            <TabPanel key={workbook.id}>
                                <Wrapper workbook={workbook} />
                            </TabPanel>
                        ))}
                    </Tabs>
                </div>
            </div>
        );
    }
}
