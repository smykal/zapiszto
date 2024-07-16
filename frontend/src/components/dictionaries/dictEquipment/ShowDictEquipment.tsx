// src/components/dictEquipment/ShowDictEquipment.tsx

import React, { useState, useEffect } from "react";
import { DictEquipment } from "../../../types/types"; 
import DictEquipmentService from '../../../services/dict/DictEquipmentService'
import AddDictEquipmentPerUser from "./AddDictEquipmentPerUser";
import Options from "./Options";
import { useTranslation } from "react-i18next";

const ShowDictEquipment: React.FC = () => {
    const [dictEquipment, setDictEquipment] = useState<DictEquipment[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictEquipment();
    }, []);

    const loadDictEquipment = () => {
        DictEquipmentService.getDictEquipment()
            .then(response => {
                setDictEquipment(response.data);
            })
            .catch(error => {
                console.error('Error loading dict equipment:', error);
            });
    };

    const handleAddEquipment = (newEquipment: DictEquipment) => {
        setDictEquipment(prevEquipment => [...prevEquipment, newEquipment]);
    };

    const handleDeleteEquipment = (equipmentId: string) => {
        setDictEquipment(prevEquipment => prevEquipment.filter(equipment => equipment.dict_id !== equipmentId));
    };

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const filteredEquipment = dictEquipment.filter(equipment => {
        if (showBasic && equipment.dict === "BASIC") return true;
        if (showPerUser && equipment.dict === "PER_USER") return true;
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
            <AddDictEquipmentPerUser dictEquipment={dictEquipment} onAddEquipment={handleAddEquipment} />
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredEquipment.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} onDeleteEquipment={handleDeleteEquipment} /> : t("table.menu_unavailable")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictEquipment;
