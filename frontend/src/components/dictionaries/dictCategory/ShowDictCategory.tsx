import React, { useEffect, useState } from "react";
import { DictCategories, NewDictCategory } from "../../../types/types";
import Service from '../../../services/dict/DictCategoryService';
import AddDictCategoryPerUser from "./AddDictCategoryPerUser";
import Options from './Options';
import { useTranslation } from "react-i18next";

const ShowDictCategory: React.FC = () => {
    const [dictCategories, setDictCategories] = useState<DictCategories[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictCategories();
    }, []);

    const loadDictCategories = () => {
        Service.getDictCategory()
            .then(response => {
                setDictCategories(response.data);
            })
            .catch(error => {
                console.error('Error loading dict categories:', error);
            });
    };

    const handleAddCategory = (newCategory: DictCategories) => {
        setDictCategories(prevCategories => [...prevCategories, newCategory]);
    };

    const handleDeleteCategory = (id: number) => {
        setDictCategories(prevCategories => prevCategories.filter(category => category.id !== id));
    };

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const filteredCategories = dictCategories.filter(category => {
        if (showBasic && category.dict === "BASIC") return true;
        if (showPerUser && category.dict === "PER_USER") return true;
        return false;
    });

    return (
        <div>
            <div>
                <label>
                    <input 
                        type="checkbox" 
                        checked={showBasic} 
                        onChange={handleShowBasicChange} 
                    />
                    {t('filter.basic')}
                </label>
                <label>
                    <input 
                        type="checkbox" 
                        checked={showPerUser} 
                        onChange={handleShowPerUserChange} 
                    />
                    {t('filter.per_user')}
                </label>
            </div>
            <AddDictCategoryPerUser dictCategory={dictCategories} onAddCategory={handleAddCategory} />
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.description")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredCategories.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.description}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.id} onDeleteCategory={handleDeleteCategory} /> : "menu niedostępne"}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictCategory;
