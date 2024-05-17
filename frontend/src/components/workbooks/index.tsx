import React, { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Service from '../../services/workbooks';
import 'react-tabs/style/react-tabs.css';
import { Workbook } from '../../types/types';
import Wrapper from './workbook';
import { withTranslation } from "react-i18next";



type Props = {
    t: any;
};
type State = {
    workbooks: Workbook[];
    newWorkbookName: string;
    activeTabIndex: number; // Dodajemy stan do przechowywania indeksu aktywnej zakładki
};

class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            workbooks: [],
            newWorkbookName: '',
            activeTabIndex: 0, // Domyślnie ustawiamy pierwszą zakładkę jako aktywną
        };
    }

    componentDidMount() {
        this.getWorkbooks();
        // Sprawdzamy, czy istnieje informacja o ostatniej aktywnej zakładce w localStorage
        const lastActiveTabIndex = localStorage.getItem('lastActiveTabIndex');
        if (lastActiveTabIndex !== null) {
            this.setState({ activeTabIndex: parseInt(lastActiveTabIndex) });
        }
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

    handleTabSelect = (index: number) => {
        this.setState({ activeTabIndex: index });
        // Zapisujemy indeks wybranej zakładki do localStorage
        localStorage.setItem('lastActiveTabIndex', index.toString());
    };

    render() {
        const { workbooks, newWorkbookName, activeTabIndex } = this.state;
        const { t } = this.props;

        return (
            <div className="container">
                <div className="input_workbook">
                    <input
                        type="text"
                        value={newWorkbookName}
                        onChange={this.handleNewWorkbookNameChange}
                        placeholder={t("workbooks.enter_new_workbook_name")}
                    />
                    <button onClick={this.handleAddWorkbook}>{t("buttons.add")}</button>
                </div>
                <div>
                    <Tabs selectedIndex={activeTabIndex} onSelect={this.handleTabSelect}>
                        <TabList>
                            {workbooks.map((workbook) => (
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

export default withTranslation("global")(Training)