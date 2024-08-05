import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { DictBodyTest, NewDictBodyTest } from "../../../types/types";
import Service from '../../../services/dict/DictBodyTestService';
import AddDictBodyTestPerUser from "./AddDictBodyTestPerUser";
import Options from './Options';

const ShowDictBodyTest: React.FC = () => {
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

    const handleAddBodyTest = (newBodyTest: NewDictBodyTest) => {
        setDictBodyTest(prevBodyTests => [
            ...prevBodyTests,
            {
                ...newBodyTest,
                id: crypto.randomUUID(),  // Generate a UUID for new test
                dict: "PER_USER",
                dict_id: crypto.randomUUID(),  // Generate a UUID for dict_id
            }
        ]);
    };

    const handleDeleteBodyTest = (id: string) => {  // Change parameter type to string for UUID
        setDictBodyTest(prevBodyTests => prevBodyTests.filter(test => test.id !== id));
    };

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
            <AddDictBodyTestPerUser dictBodyTest={dictBodyTest} onAddBodyTest={handleAddBodyTest} />
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
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
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} onDelete={handleDeleteBodyTest} /> : t("table.menu_unavailable")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictBodyTest;
