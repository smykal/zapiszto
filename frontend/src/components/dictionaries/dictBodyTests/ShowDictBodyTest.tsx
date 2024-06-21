import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { DictBodyTest } from "../../../types/types";
import Service from '../../../services/dict/DictBodyTestService';
import AddDictBodyTestPerUser from "./AddDictBodyTestPerUser";
import Options from './Options';

const ShowDictCategory: React.FC = () => {
    const [dictBodyTest, setDictBodyTest] = useState<DictBodyTest[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictBodyTest();
    }, []);

    const loadDictBodyTest = () => {
        Service.getDictBodyTest()
            .then(response => {
                setDictBodyTest(response.data);
            })
            .catch(error => {
                console.error('Error loading dict body tests:', error);
            });
    };

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const filteredBodyTests = dictBodyTest.filter(test => {
        if (showBasic && test.dict === "BASIC") return true;
        if (showPerUser && test.dict === "PER_USER") return true;
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
            <AddDictBodyTestPerUser dictBodyTest={dictBodyTest} />
            <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.description")}</th>
                        <th>{t("table.purpose")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredBodyTests.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.description}</td>
                            <td>{row.purpose}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : t("table.menu_unavailable")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictCategory;
