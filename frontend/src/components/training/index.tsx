import React, { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

type Props = {};
type State = {
    tabs: string[];
};

export default class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            tabs: ["Tab 1", "Tab 2", "Tab 3", "Tab 4"]
        };
    }

    addTab = () => {
        const { tabs } = this.state;
        const newTabNumber = tabs.length + 1;
        const newTabName = `Tab ${newTabNumber}`;
        this.setState({ tabs: [...tabs, newTabName] });
    };

    render() {
        const { tabs } = this.state;

        return (
            <div className="container">
                <Tabs>
                    <TabList>
                        {tabs.map((tab, index) => (
                            <Tab key={index}>{tab}</Tab>
                        ))}
                        <button onClick={this.addTab}>+</button>
                    </TabList>

                    {tabs.map((tab, index) => (
                        <TabPanel key={index}>
                            <h2>Any content {index + 1}</h2>
                        </TabPanel>
                    ))}
                </Tabs>
            </div>
        );
    }
}
