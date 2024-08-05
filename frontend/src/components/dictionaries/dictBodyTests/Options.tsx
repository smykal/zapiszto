import React from "react";
import Service from '../../../services/dict/DictBodyTestService';
import { withTranslation } from "react-i18next";

type Props = {
    item: string;  // Change type to string for UUID
    t: any;
    onDelete: (id: string) => void;  // Change parameter type to string for UUID
};

const Options: React.FC<Props> = ({ item, t, onDelete }) => {
    const deleteBodyTest = (itemId: string) => {  // Change parameter type to string for UUID
        Service.deleteDictBodyTest(itemId)
            .then(() => {
                onDelete(itemId);
            })
            .catch(error => {
                console.error('Error deleting body test:', error);
            });
    };

    return (
        <div>
            <button onClick={() => deleteBodyTest(item)}>{t("buttons.delete")}</button>
            <button>{t("buttons.edit")}</button>
            <button>{t("buttons.archive")}</button>
        </div>
    );
};

export default withTranslation("global")(Options);
