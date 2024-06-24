import React from "react";
import Service from '../../../services/exercises'

type Props = {
    item: number,
    onDeleteUnit: (unitId: number) => void
};

const Options: React.FC<Props> = ({ item, onDeleteUnit }) => {
    const deleteUnit = (itemId: number) => {
        Service.deleteDictUnits(itemId)
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
