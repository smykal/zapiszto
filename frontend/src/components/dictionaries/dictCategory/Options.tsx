import React from "react";
import Service from '../../../services/dict/DictCategoryService';
import { withTranslation } from "react-i18next";

type Props = {
    item: number;
    onDeleteCategory: (id: number) => void;
    t: any;
};

const Options: React.FC<Props> = ({ item, onDeleteCategory, t }) => {
    const deleteCategory = (item: number) => {
        Service.deleteDictCategory(item)
            .then(() => {
                onDeleteCategory(item);
            })
            .catch(error => {
                console.error('Error deleting category:', error);
            });
    }

    return (
        <div>
            <button onClick={() => deleteCategory(item)}>{t("buttons.delete")}</button>
            <button>{t("buttons.edit")}</button>
            <button>{t("buttons.archive")}</button>
        </div>
    );
}

export default withTranslation("global")(Options);
