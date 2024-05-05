import { Component } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import ShowDictExercises from './dictExercises/ShowDictExercises'
import ShowDictQuantityTypes from './dictQuantityType/ShowDictQuantityType'
import ShowDictUnits from "./dictUnits/ShowDictUnits";

type Props = {};
type State = {
    activeDictTabIndex: number; // Dodajemy stan do przechowywania indeksu aktywnej zakładki
};



export default class Training extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            activeDictTabIndex: 0, // Domyślnie ustawiamy pierwszą zakładkę jako aktywną
        };
    }
    componentDidMount() {
        // Sprawdzamy, czy istnieje informacja o ostatniej aktywnej zakładce w localStorage
        const lastActiveDictTabIndex = localStorage.getItem('lastActiveTabIndex');
        if (lastActiveDictTabIndex !== null) {
            this.setState({ activeDictTabIndex: parseInt(lastActiveDictTabIndex) });
        }
    }

    handleTabSelect = (index: number) => {
        this.setState({ activeDictTabIndex: index });
        // Zapisujemy indeks wybranej zakładki do localStorage
        localStorage.setItem('lastActiveDictTabIndex', index.toString());
    };

    render() {
        const { activeDictTabIndex} = this.state;

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