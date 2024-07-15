import React from "react";
import DictUnitsService from "../../../services/dict/DictUnitsService";


type Props = {
    item: string, // change type to string to match UUID
    onDeleteUnit: (unitId: string) => void // change type to string to match UUID
};

const Options: React.FC<Props> = ({ item, onDeleteUnit }) => {
    const deleteUnit = (itemId: string) => {
        DictUnitsService.deleteDictUnits(itemId)
            .then(() => {
                onDeleteUnit(itemId);
            })
            .catch(error => {
                console.error('Error deleting unit:', error);
            });
    }

    return (
        <div>
            <button onClick={() => deleteUnit(item)}>Delete {item}</button>
            <button>Edit {item}</button>
            <button>Archive {item}</button>
        </div>
    )
}

export default Options;
