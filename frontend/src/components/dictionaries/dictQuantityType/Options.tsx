import React from "react";
import { useTranslation } from "react-i18next";
import Service from '../../../services/exercises';

type Props = {
    item: string;
    onDeleteQuantityType: (id: string) => void;
};

const Options: React.FC<Props> = ({ item, onDeleteQuantityType }) => {
    const { t } = useTranslation("global");

    const deleteQuantityType = (itemId: string) => {
        Service.deleteDictQuantityType(itemId)
            .then(() => {
                console.log('Quantity type deleted:', itemId);
                onDeleteQuantityType(itemId);
            })
            .catch(error => {
                console.error('Error deleting quantity type:', error);
            });
    };

    return (
        <div>
            <button onClick={() => deleteQuantityType(item)}>{t('buttons.delete')} {item}</button>
            <button>{t('buttons.edit')} {item}</button>
            <button>{t('buttons.archive')} {item}</button>
        </div>
    );
};

export default Options;
