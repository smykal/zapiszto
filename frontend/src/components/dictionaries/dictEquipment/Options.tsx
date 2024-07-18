import React from "react";
import DictEquipmentService from "../../../services/dict/DictEquipmentService";

type Props = {
    item: string; // change type to string to match UUID
    onDeleteEquipment: (equipmentId: string) => void; // change type to string to match UUID
};

const Options: React.FC<Props> = ({ item, onDeleteEquipment }) => {
    const deleteEquipment = (itemId: string) => {
        DictEquipmentService.deleteDictEquipment(itemId)
            .then(() => {
                onDeleteEquipment(itemId);
            })
            .catch(error => {
                console.error('Error deleting equipment:', error);
            });
    }

    return (
        <div>
            <button onClick={() => deleteEquipment(item)}>Delete {item}</button>
            <button>Edit {item}</button>
            <button>Archive {item}</button>
        </div>
    );
}

export default Options;
