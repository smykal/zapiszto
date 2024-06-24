import React from "react";
import { useTranslation } from "react-i18next";
import Service from '../../../services/exercises';

type Props = {
    item: number;
    onDeleteExercise: (id: number) => void;
};

const Options: React.FC<Props> = ({ item, onDeleteExercise }) => {
    const { t } = useTranslation("global");

    const deleteExercise = (itemId: number) => {
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
