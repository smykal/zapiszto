import React, { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import ShowDictExercises from './dictExercises/ShowDictExercises'
import ShowDictQuantityTypes from './dictQuantityType/ShowDictQuantityType'
import ShowDictUnits from "./dictUnits/ShowDictUnits";

type Props = {};
type State = {
    activeDictTabIndex: number;
};

export default class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            activeDictTabIndex: 0,
        };
    }

    componentDidMount() {
        const lastActiveDictTabIndex = localStorage.getItem('lastActiveDictTabIndex');
        if (lastActiveDictTabIndex !== null) {
            this.setState({ activeDictTabIndex: parseInt(lastActiveDictTabIndex, 10) });
        }

        // Obsługa odświeżania strony
        window.addEventListener('beforeunload', this.handleBeforeUnload);
    }

    componentWillUnmount() {
        // Usuń nasłuchiwanie zdarzenia przed odmontowaniem komponentu
        window.removeEventListener('beforeunload', this.handleBeforeUnload);
    }

    handleBeforeUnload = () => {
        // Zapisz indeks aktywnej zakładki przed odświeżeniem strony
        localStorage.setItem('lastActiveDictTabIndex', this.state.activeDictTabIndex.toString());
    };

    handleTabSelect = (index: number) => {
        this.setState({ activeDictTabIndex: index });
    };

    render() {
        const { activeDictTabIndex } = this.state;

        return (
            <div>
                <Tabs selectedIndex={activeDictTabIndex} onSelect={this.handleTabSelect}>
                    <TabList>
                        <Tab key={1}>Dict Exercises</Tab>
                        <Tab key={2}>Dict Quantity Types</Tab>
                        <Tab key={3}>Dict Units</Tab>
                    </TabList>
                    <TabPanel key={1}><ShowDictExercises /></TabPanel>
                    <TabPanel key={2}><ShowDictQuantityTypes /></TabPanel>
                    <TabPanel key={3}><ShowDictUnits /></TabPanel>
                </Tabs>
            </div>
        )
    }
}
