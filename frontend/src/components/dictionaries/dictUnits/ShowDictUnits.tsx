import React, { useState, useEffect } from "react";
import { DictUnits } from "../../../types/types";
import Service from '../../../services/exercises';
import AddDictUnitPerUser from "./AddDictUnitPerUser";
import Options from "./Options";
import { useTranslation } from "react-i18next";

const ShowDictUnits: React.FC = () => {
    const [dictUnits, setDictUnits] = useState<DictUnits[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictUnits();
    }, []);

    const loadDictUnits = () => {
        Service.getDictUnits()
            .then(response => {
                setDictUnits(response.data);
            })
            .catch(error => {
                console.error('Error loading dict units:', error);
            });
    };

    const handleAddUnit = (newUnit: DictUnits) => {
        setDictUnits(prevUnits => [...prevUnits, newUnit]);
    };

    const handleDeleteUnit = (unitId: number) => {
        setDictUnits(prevUnits => prevUnits.filter(unit => unit.id !== unitId));
    };

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const filteredUnits = dictUnits.filter(unit => {
        if (showBasic && unit.dict === "BASIC") return true;
        if (showPerUser && unit.dict === "PER_USER") return true;
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
            <AddDictUnitPerUser dictUnits={dictUnits} onAddUnit={handleAddUnit} />
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
                    {filteredUnits.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.shortcut}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} onDeleteUnit={handleDeleteUnit} /> : t("table.menu_unavailable")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictUnits;
