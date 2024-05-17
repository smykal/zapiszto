import { useState, useEffect } from "react";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import ShowDictExercises from './dictExercises/ShowDictExercises'
import ShowDictQuantityTypes from './dictQuantityType/ShowDictQuantityType'
import ShowDictUnits from "./dictUnits/ShowDictUnits";
import ShowDictCategory from "./dictCategory/ShowDictCategory";
import { useTranslation } from "react-i18next";

const Training = () => {
    const [activeDictTabIndex, setActiveDictTabIndex] = useState(0);
    const { t } = useTranslation("global");

    useEffect(() => {
        const lastActiveDictTabIndex = localStorage.getItem('lastActiveDictTabIndex');
        if (lastActiveDictTabIndex !== null) {
            setActiveDictTabIndex(parseInt(lastActiveDictTabIndex, 10));
        }
    }, []); // Empty dependency array to run only once after initial render

    const handleTabSelect = (index: number) => {
        setActiveDictTabIndex(index);
        localStorage.setItem('lastActiveDictTabIndex', index.toString());
    };

    return (
        <div>
            <Tabs selectedIndex={activeDictTabIndex} onSelect={handleTabSelect}>
                <TabList>
                    <Tab>{t("dictionaries.dict_category")}</Tab>
                    <Tab>{t("dictionaries.dict_exercise")}</Tab>
                    <Tab>{t("dictionaries.dict_quantity_type")}</Tab>
                    <Tab>{t("dictionaries.dict_units")}</Tab>
                </TabList>
                <TabPanel><ShowDictCategory /></TabPanel>
                <TabPanel><ShowDictExercises /></TabPanel>
                <TabPanel><ShowDictQuantityTypes /></TabPanel>
                <TabPanel><ShowDictUnits /></TabPanel>
            </Tabs>
        </div>
    );
};

export default Training;
