import React from "react";
import { useTranslation } from "react-i18next";
import Service from '../../../services/exercises';

type Props = {
    item: string; // zmieniono na string
    onDeleteExercise: (id: string) => void; // zmieniono na string
};

const Options: React.FC<Props> = ({ item, onDeleteExercise }) => {
    const { t } = useTranslation("global");

    const deleteExercise = (itemId: string) => { // zmieniono na string
        Service.deleteDictExercise(itemId)
            .then(() => {
                console.log('Exercise deleted:', itemId);
                onDeleteExercise(itemId);
            })
            .catch(error => {
                console.error('Error deleting exercise:', error);
            });
    };

    return (
        <div>
            <button onClick={() => deleteExercise(item)}>{t('buttons.delete')} {item}</button>
            <button>{t('buttons.edit')} {item}</button>
            <button>{t('buttons.archive')} {item}</button>
        </div>
    );
};

export default Options;
