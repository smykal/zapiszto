import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { DictQuantityType } from "../../../types/types";
import Service from '../../../services/exercises';
import AddDictQuantityTypePerUser from "./AddDictQuantityTypePerUser";
import Options from './Options';

const ShowDictQuantityType: React.FC = () => {
    const [dictQuantityTypes, setDictQuantityTypes] = useState<DictQuantityType[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictQuantityTypes();
    }, []);

    const loadDictQuantityTypes = () => {
        Service.getDictQuantityType()
            .then(response => {
                setDictQuantityTypes(response.data);
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    };

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const filteredQuantityTypes = dictQuantityTypes.filter(type => {
        if (showBasic && type.dict === "BASIC") return true;
        if (showPerUser && type.dict === "PER_USER") return true;
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
            <AddDictQuantityTypePerUser dictQuantityType={dictQuantityTypes} />
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.shortcut")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredQuantityTypes.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.shortcut}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : t("table.menu_unavailable")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictQuantityType;
